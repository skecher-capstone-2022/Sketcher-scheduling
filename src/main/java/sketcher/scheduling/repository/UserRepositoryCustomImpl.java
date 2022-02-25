package sketcher.scheduling.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.QManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserSearchCondition;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QManagerHopeTime.managerHopeTime;
import static sketcher.scheduling.domain.QUser.user;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> findAllManager(UserSearchCondition condition, Pageable pageable) {
        String align = condition.getAlign();

        Sort sort = Sort.by(align).descending();

        switch (align) {
            case "username":
                sort = Sort.by(align).ascending();
                break;

            case "joindate_asc":
                sort = Sort.by("user_joindate").ascending();
                break;

            case "joindate_desc":
                sort = Sort.by("user_joindate").descending();
                break;
        }

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작 -> 페이지에서 -1 처리
        pageable = PageRequest.of(page, 10 , sort); // pageable 객체 생성 Sort.by(direction, align)

        List<User> content = queryFactory
                .selectFrom(user)
                .where(
                        managerList(condition.getType(), condition.getKeyword())
                )
                .orderBy(userSort(condition.getAlign(), pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // count쿼리는 제외하고 content 쿼리만 날린다.

        long total = queryFactory
                .selectFrom(user)
                .where(
                        managerList(condition.getType(), condition.getKeyword())
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    public List<ManagerHopeTime> findDetailById(String id) {
        List<ManagerHopeTime> content = queryFactory
                .selectFrom(managerHopeTime)
                .join(managerHopeTime.user, user)
                .where(
                        managerHopeTime.user.id.eq(user.id),
                        userIdEq(id)
                )
                .orderBy(managerHopeTime.start_time.asc())
                .fetch();

        return content;
    }

    private BooleanExpression authRoleEq(String authRole) {
        return hasText(authRole) ? user.authRole.eq(authRole) : null;
    }

    private BooleanExpression keywordContains(String type, String keyword) {
        switch (type) {
            case "username":
                return hasText(keyword) ? user.username.contains(keyword) : null;

            case "id":
                return hasText(keyword) ? user.id.contains(keyword) : null;
        }
        return null;
    }

    private BooleanBuilder managerList(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(authRoleEq("MANAGER"));
        builder.and(keywordContains(type, keyword));

        return builder;
    }

    private OrderSpecifier<?> userSort(String list_align, Pageable page) {
        //서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) { //정렬값이 들어 있으면 값을 가져온다
            switch (list_align){
                case "managerScore":
                    return new OrderSpecifier(Order.DESC, user.managerScore);

                case "username":
                    return new OrderSpecifier(Order.ASC, user.username);

                case "joindate_asc":
                    return new OrderSpecifier(Order.ASC, user.user_joinDate);

                case "joindate_desc":
                    return new OrderSpecifier(Order.DESC, user.user_joinDate);
            }
        }
        return null;
    }

    private BooleanExpression userIdEq(String userId) {
        return hasText(userId) ? user.id.eq(userId) : null;
    }
}