package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.domain.UserRole;
import ac.kr.smu.lookie.socialworker.repository.UserRepository;
import ac.kr.smu.lookie.socialworker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Map<String, ?> findById() {
        return null;
    }

    @Override
    public Map<String, ?> findByToken(String token) {
        Map<String, Objects> result = new HashMap<>();
        return null;
    }

    @Override
    public boolean register(User user) {
        User result = User.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .location(user.getLocation())
                .role(UserRole.USER)
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
        Optional<User> repo = userRepository.findByNameAndEmail(name,email);
        if(repo.isPresent())
            return repo.get().getUsername();
        else
            return null;
    }

    @Override
    public boolean findPassword(String name, String username) {
        return false;
    }

}
