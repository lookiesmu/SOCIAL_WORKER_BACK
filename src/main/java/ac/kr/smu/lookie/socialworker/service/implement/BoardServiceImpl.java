package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.Board;
import ac.kr.smu.lookie.socialworker.repository.BoardRepository;
import ac.kr.smu.lookie.socialworker.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public List<Board> getPermittedBoardList() {
        return boardRepository.findByPermit(true);
    }

    @Override
    public void register(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void permit(Long id) {
        Board board = boardRepository.findById(id).get();
        board.setPermit(true);
    }

    @Override
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
