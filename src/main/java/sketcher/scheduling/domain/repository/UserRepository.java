package sketcher.scheduling.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sketcher.scheduling.domain.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;


    /**
     * 회원 저장
     */
    public void saveUser(User user) {
        em.persist(user);
    }

    /**
     * 회원 조회
     */
    public User findOneUser(User user){

    }

    /**
     * 회원 전체 조회
     */
    public List<User> findAllUser() {

    }

    /**
     * auth_role 변경
     */
    public Integer change_auth_role(){

    }

    /**
     * 전체 관리자 조회
     */
    public List<User> findAdmin() {

    }
    /**
     * 전체 매니저 조회
     */
    public List<User> findManager() {

    }
}
