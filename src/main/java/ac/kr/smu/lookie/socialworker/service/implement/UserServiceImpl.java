package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.config.JwtTokenProvider;
import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.repository.UserRepository;
import ac.kr.smu.lookie.socialworker.service.MailService;
import ac.kr.smu.lookie.socialworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;


    @Override
    public boolean register(User user) {
        User result = User.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .location(user.getLocation())
                .role("ROLE_USER")
                .anonymity(false)
                .point(0)
                .build();
        if(userRepository.save(result) != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkUsername(String username) {
        if(userRepository.findByUsername(username).isPresent())
            return false; //중복되는 아이디 존재
        else
            return true;
    }
    @Override
    public boolean checkNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent())
            return false;
        else
            return true; //중복되는 닉네임 존재
    }

    @Override
    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            if(passwordEncoder.matches(oldPassword, user.get().getPassword())){
                userRepository.updateUserPassword(user.get().getId(), passwordEncoder.encode(newPassword));
                return true;
            }else
                return false;
        }else
            return false;
    }

    @Override
    public boolean delete(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        if(userRepository.findById(user.getId()) == null)
            return true;
        else
            return false;
    }

    @Override
    public User update(User user) {
        Optional<User> bUser = userRepository.findById(user.getId());
        if(bUser.isPresent()) {
            return userRepository.save(user);
        }else
            return null;
    }

    @Override
    public String findUsername(String name, String email) {
        Optional<User> user = userRepository.findByNameAndEmail(name,email);
        if(user.isPresent())
            return user.get().getUsername();
        else
            return null;
    }

    @Override
    public boolean findPassword(String name, String username) {
        Optional<User> user = userRepository.findByNameAndUsername(name, username);
        if(user.isPresent()){
            //이메일로 임시 비밀번호 전송
            String newPassword = passwordEncoder.encode(mailService.sendSimpleMessage(user.get().getEmail()));
            int result = userRepository.updateUserPassword(user.get().getId(),newPassword);
            if(result == 1)
                return true;
            else
                return false;
        } else{
            return false;
        }
    }

    @Override
    public String checkLogin(String username, String password){ //로그인
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent() || !passwordEncoder.matches(password, user.get().getPassword()))
            return null;
        else
            return jwtTokenProvider.createToken(String.valueOf(user.get().getId()), user.get().getRoles());
    }


}
