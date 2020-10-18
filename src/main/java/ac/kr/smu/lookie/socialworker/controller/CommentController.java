package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.Comment;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody Map<String, Object> json){
        Comment comment = (Comment) json.get("comment");
        Comment precomment = ((Comment)json.get("preComment"));
        EntityModel<Comment> body = null;

        if(precomment!=null)
            body=EntityModel.of(commentService.register(comment,precomment));
        else
            body = EntityModel.of(comment=commentService.register(comment));

        body.add(linkTo(methodOn(CommentController.class).postComment(json)).withSelfRel());

        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("commentId") Long commentId){
        return ResponseEntity.ok(commentService.delete(commentId));
    }
}
