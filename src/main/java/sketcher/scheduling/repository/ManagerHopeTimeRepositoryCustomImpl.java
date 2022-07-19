package sketcher.scheduling.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.object.HopeTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QManagerHopeTime.managerHopeTime;
import static sketcher.scheduling.domain.QUser.user;

@Repository
@Transactional
@RequiredArgsConstructor
public class ManagerHopeTimeRepositoryCustomImpl implements ManagerHopeTimeRepositoryCustom {

    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public ArrayList<String> findHopeTimeById(String id) {
        Integer code = userRepository.findById(id).get().getCode();
        List<Tuple> content = queryFactory
                .select(managerHopeTime.start_time, managerHopeTime.finish_time)
                .from(managerHopeTime)
                .join(managerHopeTime.user, user)
                .where(
                        hopeJoinUser(),
                        userCodeEq(code)
                )
                .orderBy(detailSort())
                .fetch();

        ArrayList<String> hope = new ArrayList<>();

        return makeHopeStr(content, hope);
    }

    @Override
    public void deleteByUserId(String id) {
        Integer code = userRepository.findById(id).get().getCode();

        long execute = queryFactory
                .delete(managerHopeTime)
                .where(userCodeEq(code))
                .execute();
    }

    private BooleanExpression hopeJoinUser() {
        return managerHopeTime.user.code.eq(user.code);
    }

    private BooleanExpression userCodeEq(Integer userCode) {
        return hasText(String.valueOf(userCode)) ? managerHopeTime.user.code.eq(userCode) : null;
    }

    private OrderSpecifier<?> detailSort() {
        if (user.managerHopeTimeList != null) {
            return new OrderSpecifier(Order.ASC, managerHopeTime.start_time);
        }
        return null;
    }

    private ArrayList<String> makeHopeStr(List<Tuple> content, ArrayList<String> hope) {
        for (Tuple tuple : content) {
            Integer start_time = tuple.get(managerHopeTime.start_time);
            Integer finish_time = tuple.get(managerHopeTime.finish_time);

            Optional<HopeTime> starHopeTime = HopeTime.valueOfStarTime(start_time);
            Optional<HopeTime> finishHopeTime = HopeTime.valueOfFinishTime(finish_time);

            if (isEqualHopeTime(starHopeTime, finishHopeTime))
                hope.add(starHopeTime.get().getKor() + " " + start_time + "시 ~ " + finish_time + "시");
        }

        return hope;
    }

    private boolean isEqualHopeTime(Optional<HopeTime> starHopeTime, Optional<HopeTime> finishHopeTime) {
        if (starHopeTime.equals(finishHopeTime))
            return true;

        return false;
    }

}