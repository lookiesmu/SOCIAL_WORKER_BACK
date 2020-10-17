package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.Comment;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.repository.CommentRepository;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentList(Long postId) {
        return commentRepository.findByPostOrderByCreateDateDesc(Post.builder().id(postId).build());
    }

    @Override
    public Comment register(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment register(Comment comment, Comment recomment) {
        comment.setId(commentRepository.save(comment).getId());
        commentRepository.addRecomment(recomment.getId(),comment.getId());

        return comment;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        Map<String, Boolean> result = new HashMap<>();

        try{
            commentRepository.deleteById(id);
            result.put("success",true);
        }catch (Exception e){result.put("success",false);}

        return result;
    }
}
