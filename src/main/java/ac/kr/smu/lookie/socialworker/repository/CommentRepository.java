package ac.kr.smu.lookie.socialworker.repository;

import ac.kr.smu.lookie.socialworker.domain.Comment;
import ac.kr.smu.lookie.socialworker.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findByPostOrderByCreateDateDesc(Post post);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO COMMENT_RECOMMENT_LIST VALUES(:preCommentId, :recommentId)", nativeQuery=true)
    public int addRecomment(@Param("preCommentId") Long preCommentId, @Param("recommentId") Long recommentId);

    public void deleteByPost(Post post);
}
