package ac.kr.smu.lookie.socialworker.repository;

import ac.kr.smu.lookie.socialworker.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    public List<Board> findByPermit(boolean permit);
}
