package ac.kr.smu.lookie.socialworker.controller;


import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import ac.kr.smu.lookie.socialworker.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"{boardId}/posts", "/posts"})
@Slf4j
public class PostController {

    private final PostService postService;
    private final int PAGE_SIZE = 25;

    @GetMapping
    public ResponseEntity<?> getPostList(@PathVariable("boardId") Long boardId,@RequestParam int page){//게시판 글 목록
        Pageable pageable = PageRequest.of(page-1,PAGE_SIZE);

        Page<Post> postList = postService.getPostList(boardId, pageable);
        PageMetadata pageMetadata = new PageMetadata(postList.getSize(), postList.getNumber(), postList.getTotalElements());
        PagedModel<Post> body = PagedModel.of(postList.getContent(),pageMetadata);

        body.add(linkTo(methodOn(PostController.class).getPostList(boardId,page)).withSelfRel());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId){//글 상세보기
        Post post = postService.getPost(postId);
        EntityModel<Post> body = EntityModel.of(post);

        body.add(linkTo(methodOn(PostController.class).getPost(postId)).withSelfRel());
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<?> postPost(@PathVariable("boardId") Long boardId, @RequestBody Post post){//글 등록
        EntityModel<Post> body = EntityModel.of(postService.register(post));

        body.add(linkTo(methodOn(PostController.class).postPost(boardId,post)).withSelfRel());
        return new ResponseEntity<>(body,HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> putPost(@RequestBody Post post, @AuthenticationPrincipal User user){//글 수정

        if(!user.equals(postService.getPost(post.getId()).getUser())){
            return ResponseEntity.status(403).build();
        }

        EntityModel<Post> body = EntityModel.of(postService.update(post));

        body.add(linkTo(methodOn(PostController.class).putPost(post, user)).withSelfRel());
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> patchPost(@PathVariable("postId") Long postId, @AuthenticationPrincipal User user){//좋아요 처리
        postService.like(postId, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal User user){

        if(!user.equals(postService.getPost(postId).getUser()) || !user.getRoles().contains("ADMIN")){
            return ResponseEntity.status(403).build();
        }
        
        return ResponseEntity.ok(postService.delete(postId));
    }
}
