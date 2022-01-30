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
     * 회원 아이디로 조회
     */
    public User findById(User user){

    }

    /**
     * 회원 이름으로 조회
     */
    public User findByName(User user){
        
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


    /**
     * 회원 탈퇴 요청
     */
    public Integer deleteUserRequest(){

    }

    /**
     * 회원 탈퇴 요청 조회
     */
    public List<User> findDeleteUserRequest(){

    }

    /**
     * 회원 삭제
     */
    public Integer deleteUser(){
        
    }

    /**
     * 회원 정보 수정
     */
    public void updateUser(){

    }
}
