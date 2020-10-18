package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.Board;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.repository.FileRepository;
import ac.kr.smu.lookie.socialworker.repository.PostRepository;
import ac.kr.smu.lookie.socialworker.service.CheckSuccessDeleteService;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import ac.kr.smu.lookie.socialworker.service.FileService;
import ac.kr.smu.lookie.socialworker.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final FileService fileService;
    private final CheckSuccessDeleteService deleteService;

    @Override
    public Page<Post> getPostList(Long boardId, Pageable pageable) {
        return postRepository.findByBoardOrderByCreatedDate(Board.builder().id(boardId).build(),pageable);
    }

    @Override
    public Post getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);

        if(post!=null)
            post.setCommentList(commentService.getCommentList(post.getId()));

        return post;
    }

    @Override
    public Post register(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post update(Post post) {
        Post origin = postRepository.getOne(post.getId());

        origin.update(post);
        return postRepository.save(post);
    }

    @Override
    public void like(Long id, User user) {
        Post post = postRepository.getOne(id);

        if(post.getLike().contains(user))
            post.getLike().remove(user);
        else
            post.getLike().add(user);

        postRepository.save(post);
    }

    @Override
    @Transactional
    public Map<String, Boolean> delete(Long id) {
        Map<String, Boolean> result;
        try{
            fileService.deleteByPost(Post.builder().id(id).build());
            commentService.deleteByPost(Post.builder().id(id).build());

            result=deleteService.delete(postRepository, id);
        }catch (Exception e){
            result = new HashMap<>();
            result.put("success",false);
        }
        return result;
    }
}
