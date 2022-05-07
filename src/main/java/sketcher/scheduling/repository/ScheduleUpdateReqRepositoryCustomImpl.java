package sketcher.scheduling.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ScheduleUpdateReq;

import java.util.ArrayList;
import java.util.List;

import static sketcher.scheduling.domain.QScheduleUpdateReq.scheduleUpdateReq;

@Repository
@Transactional
@RequiredArgsConstructor
public class ScheduleUpdateReqRepositoryCustomImpl implements ScheduleUpdateReqRepositoryCustom{

    private final ScheduleUpdateReqRepository updateReqRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ScheduleUpdateReq> sort(String sort) {

        QueryResults<ScheduleUpdateReq> content = queryFactory
                .selectFrom(scheduleUpdateReq)
                .where(scheduleUpdateReq.reqAcceptCheck.eq('N'))
                .orderBy(updateReqSort(sort))
                .fetchResults();

        return content.getResults();
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
