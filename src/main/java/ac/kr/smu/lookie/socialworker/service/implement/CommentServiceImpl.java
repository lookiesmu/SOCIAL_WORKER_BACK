package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.Comment;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.repository.CommentRepository;
import ac.kr.smu.lookie.socialworker.service.CheckSuccessDeleteService;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CheckSuccessDeleteService deleteService;

    @Override
    public Comment getComment(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public List<Comment> getCommentList(Long postId) {
        return commentRepository.findByPostOrderByCreateDateDesc(Post.builder().id(postId).build());
    }

    @Override
    public Comment register(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment register(Comment recomment, Long precommentId) {
        recomment.setId(commentRepository.save(recomment).getId());
        commentRepository.addRecomment(recomment.getId(), precommentId);

        return recomment;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        return deleteService.delete(commentRepository, id);
    }

    @Override
    public void deleteByPost(Post post) {
        commentRepository.deleteByPost(post);
    }
}
