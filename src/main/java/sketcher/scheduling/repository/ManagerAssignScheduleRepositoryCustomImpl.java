package sketcher.scheduling.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.config.LocalDateTimeConfig;
import sketcher.scheduling.object.OrderByNull;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QManagerAssignSchedule.managerAssignSchedule;
import static sketcher.scheduling.domain.QUser.user;

@Repository
@Transactional
@RequiredArgsConstructor
public class ManagerAssignScheduleRepositoryCustomImpl implements ManagerAssignScheduleRepositoryCustom {

    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    final int DAYOFMONTH = 21;

    LocalDateTimeConfig ldt = new LocalDateTimeConfig();

    @Override
    public List<ManagerAssignSchedule> findByUserId(String id) {
        Integer code = getUserCode(id);

        return queryFactory
                .selectFrom(managerAssignSchedule)
                .where(userCodeEq(code),
                        week_assign()) // 주단위 스케줄 가져옴
                .orderBy(managerAssignSchedule.scheduleDateTimeStart.asc())
                .fetch();
    }

    @Override
    public long weekAssignByUserId(String id) {
        Integer code = getUserCode(id);

        List<Tuple> assignSchedule = queryFactory
                .select(managerAssignSchedule.scheduleDateTimeStart,
                        managerAssignSchedule.scheduleDateTimeEnd)
                .from(managerAssignSchedule)
                .where(userCodeEq(code),
                        week_assign())
                .fetch();

        return schedule_hours(assignSchedule);
    }

    @Override
    public long weekWorkByUserId(String id) {
        Integer code = getUserCode(id);

        List<Tuple> assignSchedule = queryFactory
                .select(managerAssignSchedule.scheduleDateTimeStart,
                        managerAssignSchedule.scheduleDateTimeEnd)
                .from(managerAssignSchedule)
                .where(userCodeEq(code),
                        week_work())
                .fetch();

        return schedule_hours(assignSchedule);
    }

    @Override
    public long weekRemainByUserId(String id) {
        Integer code = getUserCode(id);

        List<Tuple> assignSchedule = queryFactory
                .select(managerAssignSchedule.scheduleDateTimeStart,
                        managerAssignSchedule.scheduleDateTimeEnd)
                .from(managerAssignSchedule)
                .where(userCodeEq(code),
                        week_remain())
                .fetch();

        return schedule_hours(assignSchedule);
    }

    @Override
    public long monthAssignWorkByUserId(String id) {
        Integer code = getUserCode(id);

        List<Tuple> assignSchedule = queryFactory
                .select(managerAssignSchedule.scheduleDateTimeStart,
                        managerAssignSchedule.scheduleDateTimeEnd)
                .from(managerAssignSchedule)
                .where(userCodeEq(code),
                        month_assign())
                .orderBy(OrderByNull.DEFAULT) // 인덱스가 없는 group by 쿼리는 filesort 때문에 성능 느림 -> order by null
                .fetch();

        return schedule_hours(assignSchedule);
    }

    @Override
    public long countByTodayAssignManager() {
        return queryFactory
                .select(user.code)
                .from(managerAssignSchedule)
                .where(today_assign())
                .join(managerAssignSchedule.user, user)
                .groupBy(user.code)
                .fetchCount();
    }

    private BooleanExpression userCodeEq(Integer userCode) {
        return hasText(String.valueOf(userCode)) ? managerAssignSchedule.user.code.eq(userCode) : null;
    }

    public BooleanExpression week_assign() {
        LocalDateTime date = ldt.getLocalTimeNooN();
        int dayOfWeekNumber = ldt.getDayOfWeekNumber(date);
        LocalDateTime start = date.minusDays(dayOfWeekNumber);
        LocalDateTime end = date.plusDays(7 - dayOfWeekNumber);

        return managerAssignSchedule.scheduleDateTimeStart.between(start, end)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(start, end));

        //BETWEEN '2022-03-11' AND '2022-03-12'은
        //2022-03-11 00:00:00 이상, 2022-03-12 00:00:00 미만
    }

    public BooleanExpression week_work() {
        LocalDateTime dateTime = LocalDateTime.now(); // 오늘 날짜 가져옴
        LocalDateTime date = ldt.getLocalTimeNooN();

        LocalDateTime weekStart = ldt.getWeekStart(date);

        return managerAssignSchedule.scheduleDateTimeStart.between(weekStart, dateTime)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(weekStart, dateTime));
    }

    public BooleanExpression week_remain() {
        LocalDateTime dateTime = LocalDateTime.now(); // 오늘 날짜 가져옴
        LocalDateTime date = ldt.getLocalTimeNooN();

        LocalDateTime weekEnd = ldt.getWeekEnd(date);

        return managerAssignSchedule.scheduleDateTimeStart.between(dateTime, weekEnd)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(dateTime, weekEnd));
    }

    public BooleanExpression month_assign() {
//        LocalDateTime dateTime = LocalDateTime.now(); // 오늘 날짜 가져옴
        LocalDateTime date = ldt.getLocalTimeNooN();

//        LocalDateTime start = date.with(TemporalAdjusters.firstDayOfMonth()); // 이번 달의 1일
//        LocalDateTime end = date.with(TemporalAdjusters.firstDayOfNextMonth()); // 다음 달의 1일

        int dayOfWeekNumber = ldt.getDayOfWeekNumber(date);

        LocalDateTime start = date.minusDays(dayOfWeekNumber + DAYOFMONTH);
        LocalDateTime end = date.plusDays(7 - dayOfWeekNumber);

        return managerAssignSchedule.scheduleDateTimeStart.between(start, end)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(start, end));
    }

    public BooleanExpression today_assign() {
        LocalDateTime date = ldt.getLocalTimeNooN();
        LocalDateTime end = ldt.getLocalTimeMAX();

        return managerAssignSchedule.scheduleDateTimeStart.between(date, end)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(date, end));
    }

    public Integer getUserCode(String id) {
        return userRepository.findById(id).get().getCode();
    }

    public long schedule_hours(List<Tuple> assignSchedule) {
        long sum = 0;
        Duration duration;

        for (Tuple tuple : assignSchedule) {
            LocalDateTime from = tuple.get(managerAssignSchedule.scheduleDateTimeStart);
            LocalDateTime to = tuple.get(managerAssignSchedule.scheduleDateTimeEnd);
            duration = Duration.between(from, to);
            sum += duration.toHours();
        }

        return sum;
    }
}
