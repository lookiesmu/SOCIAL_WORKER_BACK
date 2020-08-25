package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.User;

import java.util.Map;
import java.util.Objects;

public interface UserService {

    public Map<String, ?> findById(); //회원정보 조회
    public Map<String, ?> findByToken(String token);

    public boolean register(User user); //회원가입
    public boolean checkUsername(String username); //중복 아이디 검사
    public boolean checkNickname(String nickname); //중복 닉네임 검사
    public boolean updatePassword(String oldPassword, String newPassword); //비밀번호 수정
    public User update(User user); //회원 정보 수정
    public boolean delete(Long id); //회원 정보 삭제
    public String findUsername(String name, String email); //아이디찾기
    public boolean findPassword(String name, String username); //비밀번호 찾기

}
