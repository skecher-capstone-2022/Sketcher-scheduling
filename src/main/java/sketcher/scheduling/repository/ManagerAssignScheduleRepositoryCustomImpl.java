package sketcher.scheduling.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QManagerAssignSchedule.managerAssignSchedule;

@Repository
@Transactional
@RequiredArgsConstructor
public class ManagerAssignScheduleRepositoryCustomImpl implements ManagerAssignScheduleRepositoryCustom {

    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ManagerAssignSchedule> findByUserId(String id) {
        Integer code = userRepository.findById(id).get().getCode();

        return queryFactory
                .selectFrom(managerAssignSchedule)
                .where(userCodeEq(code),
                        week()) // 주단위 스케줄 가져옴
                .orderBy(managerAssignSchedule.scheduleDateTimeStart.asc())
                .fetch();
    }

    private BooleanExpression userCodeEq(Integer userCode) {
        return hasText(String.valueOf(userCode)) ? managerAssignSchedule.user.code.eq(userCode) : null;
    }

    public BooleanExpression week() {
        LocalDateTime date = LocalDateTime.now() // 오늘 날짜 가져옴
                        .withHour(0)      // 시간 변경
                        .withMinute(0)    // 분 변경
                        .withSecond(0)    // 초 변경
                        .withNano(0);     // 나노초 변경


        DayOfWeek dayOfWeek = date.getDayOfWeek(); // 오늘 날짜의 요일 가져옴
        int dayOfWeekNumber = dayOfWeek.getValue() - 1; // 요일의 지정 숫자에서 1을 뺌 (월: 0)

        LocalDateTime start = date.minusDays(dayOfWeekNumber);
        LocalDateTime end = date.plusDays(7 - dayOfWeekNumber);

        return managerAssignSchedule.scheduleDateTimeStart.between(start, end)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(start, end));

        //BETWEEN '2022-03-11' AND '2022-03-12'은
        //2022-03-11 00:00:00 이상, 2022-03-12 00:00:00 미만
    }
}
