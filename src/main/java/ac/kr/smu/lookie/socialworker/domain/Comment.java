package ac.kr.smu.lookie.socialworker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    @CreationTimestamp
    private Timestamp createDate;

    @Column
    @UpdateTimestamp
    private Timestamp modifyDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable
    @Builder.Default
    private List<Comment> recommentList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn
    private Post post;

    public void addRecomment(Comment recomment){
        recommentList.add(recomment);
    }
}
