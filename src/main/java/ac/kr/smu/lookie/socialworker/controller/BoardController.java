package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.Board;
import ac.kr.smu.lookie.socialworker.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> getBoard(){
        CollectionModel<Board> body = CollectionModel.of(boardService.getPermittedBoardList());

        body.add(linkTo(methodOn(BoardController.class).getBoard()).withSelfRel());
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<?> postBoard(@RequestBody Board board){
        boardService.register(board);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<?> patchBoard(@PathVariable("boardId") Long boardId){//게시판 승인
        boardService.permit(boardId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable("boardId") Long boardId){
        boardService.delete(boardId);

        return ResponseEntity.noContent().build();
    }
}
