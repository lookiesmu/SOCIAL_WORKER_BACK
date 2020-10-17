package ac.kr.smu.lookie.socialworker;

import ac.kr.smu.lookie.socialworker.domain.*;
import ac.kr.smu.lookie.socialworker.repository.BoardRepository;
import ac.kr.smu.lookie.socialworker.repository.CommentRepository;
import ac.kr.smu.lookie.socialworker.repository.PostRepository;
import ac.kr.smu.lookie.socialworker.repository.UserRepository;
import ac.kr.smu.lookie.socialworker.service.BoardService;
import ac.kr.smu.lookie.socialworker.service.CommentService;
import ac.kr.smu.lookie.socialworker.service.PostService;
import ac.kr.smu.lookie.socialworker.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocialworkerApplicationTests {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {

        commentService.register(Comment.builder().content("대댓글테스트2").build(),Comment.builder().id(2L).build());
    }

}
