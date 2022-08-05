package sketcher.scheduling.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.dto.ManagerHopeTimeDto;
import sketcher.scheduling.dto.UserDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
        List<Integer> content = queryFactory
                .select(managerHopeTime.start_time)
                .from(managerHopeTime)
                .join(managerHopeTime.user, user)
                .where(
                        hopeJoinUser(),
                        userCodeEq(code)
                )
                .orderBy(detailSort())
                .fetch();

        ArrayList<String> hope = new ArrayList<>();
        for (Integer start_time : content) {
            switch (start_time) {
                case 0:
                    hope.add("새벽 0시 ~ 6시");
                    break;

                case 6:
                    hope.add("오전 6시 ~ 12시");
                    break;

                case 12:
                    hope.add("오후 12시 ~ 18시");
                    break;

                case 18:
                    hope.add("저녁 18시 ~ 24시");
                    break;
            }
        }

        return hope;
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

}
