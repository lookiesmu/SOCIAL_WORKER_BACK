package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.config.JwtTokenProvider;
import ac.kr.smu.lookie.socialworker.domain.Role;
import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.domain.UserRole;
import ac.kr.smu.lookie.socialworker.repository.UserRepository;
import ac.kr.smu.lookie.socialworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

//    @Override
//    public UserDetails findByAuthenticationUser(String userPk) {
//        return userRepository.findById(Long.valueOf(userPk)).orElse(null);
//    }


    @Override
    public boolean register(User user) {
        User result = User.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .location(user.getLocation())
                .role(user.getRole())
                .anonymity(0)
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
            return true;
        else
            return false;
    }
    @Override
    public boolean checkNickname(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent())
            return true;
        else
            return false;
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public User update(User user) {
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
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String checkLogin(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent() || passwordEncoder.matches(password, user.get().getPassword()))
            return null;
        else
            return jwtTokenProvider.createToken(String.valueOf(user.get().getId()), user.get().getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        return userRepository.findById(Long.valueOf(userPk)).orElseThrow(NullPointerException::new);
    }
}
