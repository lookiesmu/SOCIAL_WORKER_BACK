package ac.kr.smu.lookie.socialworker.repository;

import ac.kr.smu.lookie.socialworker.domain.Board;
import ac.kr.smu.lookie.socialworker.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    public Page<Post> findByBoardOrderByCreatedDate(Board board, Pageable pageable);

    public void deleteById(Long id);

    public Page<Post> findByHotIsTrue(Pageable pageable);
}
