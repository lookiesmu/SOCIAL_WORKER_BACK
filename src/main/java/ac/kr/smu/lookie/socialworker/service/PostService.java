package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.Board;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface PostService {

    public Page<Post> getPostList(Board board, Pageable pageable);

    public Post getPost(Long id);

    public Post register(Post post);

    public Post update(Post post);

    public void like(Long id, User user);//좋아요

    public Map<String, Boolean> delete(Long id);
}
