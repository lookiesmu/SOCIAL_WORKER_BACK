package ac.kr.smu.lookie.socialworker.repository;

import ac.kr.smu.lookie.socialworker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findByUsername(String username);
    public Optional<User> findByNickname(String nickname);
    public Optional<User> findByNameAndEmail(String name, String email);
    public Optional<User> findByNameAndUsername(String name, String username);
}
