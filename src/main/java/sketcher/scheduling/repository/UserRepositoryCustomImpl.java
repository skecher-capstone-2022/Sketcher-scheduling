//package sketcher.scheduling.repository;
//
//import com.querydsl.core.QueryResults;
//import com.querydsl.core.types.dsl.BooleanExpression;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.support.PageableExecutionUtils;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.dto.UserSearchCondition;
//
//import java.util.List;
//
//import static org.springframework.util.StringUtils.hasText;
//import static sketcher.scheduling.domain.QUser.user;
//
//public class UserRepositoryCustomImpl implements UserRepositoryCustom {
//    private final JPAQueryFactory queryFactory;
//
//    public UserRepositoryCustomImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
//
//    @Override
//    public List<User> search(UserSearchCondition condition) {
//        return null;
//    }
//
//    @Override
//    public Page<User> searchPageSimple(UserSearchCondition condition, Pageable pageable) {
//        QueryResults<User> results = queryFactory
//                .selectFrom(user)
//                .where(
//                        user.authRole.eq("MANAGER")
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults(); // querydsl 이 content용 쿼리, count용 쿼리 2개를 날린다.
//
//        List<User> content = results.getResults();
//        long total = results.getTotal();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//
//    @Override
//    public Page<User> searchPageComplex(UserSearchCondition condition, Pageable pageable) {
//        List<User> content = queryFactory
//                .selectFrom(user)
//                .where(
//                        authRoleEq("MANAGER"),
//                        usernameContains(condition.getUsername()),
//                        userIdContains(condition.getId())
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch(); // count쿼리는 제외하고 content 쿼리만 날린다.
//
//        JPAQuery<User> countQuery = queryFactory
//                .selectFrom(user)
//                .where(
//                        authRoleEq("MANAGER"),
//                        usernameContains(condition.getUsername()),
//                        userIdContains(condition.getId())
//                );
//
//        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
//    }
//
//    private BooleanExpression authRoleEq(String authRole) {
//        return hasText(authRole) ? user.authRole.eq(authRole) : null;
//    }
//
//    private BooleanExpression usernameContains(String username) {
//        return hasText(username) ? user.username.contains(username) : null;
//    }
//
//    private BooleanExpression userIdContains(String id) {
//        return hasText(id) ? user.id.contains(id) : null;
//    }
//}
