package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.Board;

import java.util.List;

public interface BoardService {

    public List<Board> getBoardList();//Admin 유저를 위한 모든 게시판 조회

    public List<Board> getPermittedBoardList();//허가된 게시판 조회

    public void register(Board board);

    public void permit(Long id);

    public void delete(Long id);
}
