package ac.kr.smu.lookie.socialworker.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @CreationTimestamp
    private Date createdDate;

    @Column
    @UpdateTimestamp
    private Date modifiedDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "POST_LIKE", joinColumns = @JoinColumn(name = "POST_ID"), inverseJoinColumns = @JoinColumn(name = "LIKE_ID"))
    @Builder.Default
    private List<User> like=new ArrayList<>();//좋아요를 누른 유저 리스트

    @OneToOne(fetch = FetchType.LAZY)
    private User user;//글 쓴 유저

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @Builder.Default
    private List<FileInfo> fileList=new ArrayList<>();//파일들

    @OneToOne
    private Board board;//게시판

    @Transient
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    public void update(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
