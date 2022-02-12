//package sketcher.scheduling.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import sketcher.scheduling.domain.User;
//import sketcher.scheduling.repository.UserRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class UserService {
//
//        private final UserRepository userRepository;
//
//        /**
//         * 회원 가입
//         */
//        @Transactional
//        public Integer join(User user){
//                validateDuplicateUser(user);
//        }
//
//        /**
//         * 회원 아이디로 조회
//         */
//        public User findById(User user){
//
//        }
//
//        /**
//         * 회원 이름으로 조회
//         */
//        public User findByName(User user){
//
//        }
//        /**
//         * 회원 전체 조회
//         */
//        public List<User> findAllUser() {
//
//        }
//
//        /**
//         * auth_role 변경
//         */
//        @Transactional
//        public Integer change_auth_role(){
//
//        }
//
//        /**
//         * 관리자 조회
//         */
//        public List<User> findAdmin() {
//
//        }
//        /**
//         * 매니저 조회
//         */
//        public List<User> findManager() {
//
//        }
//
//        /**
//         * 회원 탈퇴 요청
//         */
//        @Transactional
//        public Integer deleteUserRequest(){
//
//        }
//
//        /**
//         * 회원 탈퇴 요청 조회
//         */
//        public List<User> findDeleteUserRequest(){
//
//        }
//
//        /**
//         * 회원 삭제
//         */
//        @Transactional
//        public Integer deleteUser(){
//
//        }
//
//        /**
//         * 회원 정보 수정
//         */
//        @Transactional
//        public void updateUser(){
//
//        }
//
//        /**
//         * 이미 존재하는 회원 아이디 검증
//         */
//        private void validateDuplicateUser(User user) {
//        }
//
//}
