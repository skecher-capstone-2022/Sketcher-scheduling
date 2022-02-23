package sketcher.scheduling.service;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sketcher.scheduling.domain.ManagerHopeTime;
import sketcher.scheduling.domain.User;
import sketcher.scheduling.dto.UserDto;
import sketcher.scheduling.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static sketcher.scheduling.domain.QUser.user;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final UserRepositoryCustom userRepositoryCustom;

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<ManagerHopeTime> findDetailById(String id) {
//        List<ManagerHopeTime> details = userRepository.findDetailById(id);
        return userRepository.findDetailById(id);
    }


    @Transactional(readOnly = true)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> findAllManager(String list_align, String type, String keyword, Pageable pageable) {
        Sort.Direction direction = Sort.Direction.DESC;
        String align = list_align;

        switch (list_align) {
            case "username":
                direction = Sort.Direction.ASC;
                break;

            case "user_joindate_asc":
                direction = Sort.Direction.ASC;
                align = "user_joindate";
                break;

            case "user_joindate_desc":
                align = "user_joindate";
                break;

            default:
                align = "managerScore";
                break;
        }

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작 -> 페이지에서 -1 처리
        pageable = PageRequest.of(page, 10 , Sort.by(direction, align)); // pageable 객체 생성

        List<User> content = queryFactory
                .selectFrom(user)
                .where(
                        managerList(type, keyword)
                )
                .orderBy(userSort(list_align, pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // count쿼리는 제외하고 content 쿼리만 날린다.

        long total = queryFactory
                .selectFrom(user)
                .where(
                        managerList(type, keyword)
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
//        return userRepository.findAllManager(type, keyword, pageable);
    }

    /**
     * 저장하고 id 값 리턴
     */
    @Transactional
    public String saveUser(UserDto user){
        return userRepository.save(user.toEntity()).getId();
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

                    case "user_joindate_asc":
                        return new OrderSpecifier(Order.ASC, user.user_joindate);

                    case "user_joindate_desc":
                        return new OrderSpecifier(Order.DESC, user.user_joindate);
                }
            }
        return null;
    }
}
