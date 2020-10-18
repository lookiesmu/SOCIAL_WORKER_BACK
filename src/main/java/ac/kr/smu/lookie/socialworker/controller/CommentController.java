package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.Comment;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequiredArgsConstructor
@RequestMapping({"/{postId}/comments", "/comments"})
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody Comment comment, @PathVariable("postId") Long postId) {
        comment.setPost(Post.builder().id(postId).build());

        EntityModel<Comment> body = EntityModel.of(comment = commentService.register(comment));
        body.add(linkTo(methodOn(CommentController.class).postComment(comment, postId)).withSelfRel());

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PostMapping("/{preCommmnetId}")
    public ResponseEntity<?> postRecomment(@RequestBody Comment comment, @PathVariable("postId") Long postId,
                                           @PathVariable("preCommentId") Long preCommentId) {
        comment.setPost(Post.builder().id(postId).build());
        
        EntityModel<Comment> body = EntityModel.of(commentService.register(preCommentId, comment));
        body.add(linkTo(methodOn(CommentController.class).postRecomment(comment, postId, preCommentId)).withSelfRel());

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.delete(commentId));
    }
}
