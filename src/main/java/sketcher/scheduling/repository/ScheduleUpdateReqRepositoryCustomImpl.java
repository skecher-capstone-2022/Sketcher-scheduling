package sketcher.scheduling.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.config.LocalDateTimeConfig;
import sketcher.scheduling.domain.ScheduleUpdateReq;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QScheduleUpdateReq.scheduleUpdateReq;

@Repository
@Transactional
@RequiredArgsConstructor
public class ScheduleUpdateReqRepositoryCustomImpl implements ScheduleUpdateReqRepositoryCustom{

    private final ScheduleUpdateReqRepository updateReqRepository;
    private final JPAQueryFactory queryFactory;
    LocalDateTimeConfig ldt = new LocalDateTimeConfig();

    @Override
    public List<ScheduleUpdateReq> sort(String sort) {

        QueryResults<ScheduleUpdateReq> content = queryFactory
                .selectFrom(scheduleUpdateReq)
                .where(scheduleUpdateReq.reqAcceptCheck.eq('N'))
                .orderBy(updateReqSort(sort))
                .fetchResults();

        return content.getResults();
    }

    @Override
    public long countByWeekNotAcceptUpdateReq() {
        return queryFactory
                .select(scheduleUpdateReq.id)
                .from(scheduleUpdateReq)
                .where(reqAcceptCheckEq("N"),
                        week_request())
                .fetchCount();
    }

    @Override
    public long countByWeekUpdateReq() {
        return queryFactory
                .select(scheduleUpdateReq.id)
                .from(scheduleUpdateReq)
                .where(week_request())
                .fetchCount();
    }

    private BooleanExpression reqAcceptCheckEq(String check) {
        return hasText(check) ? scheduleUpdateReq.reqAcceptCheck.eq('N') : null;
    }

    public BooleanExpression week_request() {
        LocalDateTime date = ldt.getLocalTimeNooN();

        LocalDateTime start = ldt.getWeekStart(date);
        LocalDateTime end = ldt.getWeekEnd(date);

        return scheduleUpdateReq.reqTime.between(start, end);
    }


    private OrderSpecifier<?> updateReqSort(String sort) {
        switch (sort) {
            case "req_date_desc":
                return new OrderSpecifier(Order.DESC, scheduleUpdateReq.reqTime);

            case "req_date_asc":
                return new OrderSpecifier(Order.ASC, scheduleUpdateReq.reqTime);
        }
        return null;
    }

    @Override
    public ArrayList<String> findDetailById(String id) {
        return null;
    }


}
