package ac.kr.smu.lookie.socialworker.repository;



import ac.kr.smu.lookie.socialworker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
    public Optional<User> findByNickname(String nickname);
    public Optional<User> findByNameAndEmail(String name, String email);
    public Optional<User> findByNameAndUsername(String name, String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE User u SET u.password = :newPassword where u.id = :id", nativeQuery = false)
    public int updateUserPassword(Long id, String newPassword);
}