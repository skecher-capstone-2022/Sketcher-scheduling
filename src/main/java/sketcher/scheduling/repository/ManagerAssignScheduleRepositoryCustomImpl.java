package sketcher.scheduling.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerAssignSchedule;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.QManagerAssignSchedule;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
                        week())
                .orderBy(managerAssignSchedule.scheduleDateTimeStart.asc())
                .fetch();
    }

    private BooleanExpression userCodeEq(Integer userCode) {
        return hasText(String.valueOf(userCode)) ? managerAssignSchedule.user.code.eq(userCode) : null;
    }

    public BooleanExpression week() {
        LocalDateTime date = LocalDateTime.now()
                        .withHour(0)      // 시간 변경
                        .withMinute(0)    // 분 변경
                        .withSecond(0)    // 초 변경
                        .withNano(0);     // 나노초 변경


        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue() - 1;

        LocalDateTime start = date.minusDays(dayOfWeekNumber);
        LocalDateTime end = date.plusDays(dayOfWeekNumber + 7);

        return managerAssignSchedule.scheduleDateTimeStart.between(start, end)
                .and(managerAssignSchedule.scheduleDateTimeEnd.between(start, end));
    }
}
