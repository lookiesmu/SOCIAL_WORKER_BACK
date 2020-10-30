package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.Comment;
import ac.kr.smu.lookie.socialworker.domain.Post;

import java.util.List;
import java.util.Map;

public interface CommentService {

    public Comment getComment(Long id);

    public List<Comment> getCommentList(Long postId);

    public Comment register(Comment comment);

    public Comment register(Long preCommentId,Comment comment);

    public Map<String, Boolean> delete(Long id);

    public void deleteByPost(Post post);
}
