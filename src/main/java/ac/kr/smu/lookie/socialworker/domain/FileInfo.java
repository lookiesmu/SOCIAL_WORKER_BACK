package ac.kr.smu.lookie.socialworker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class FileInfo {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String  uuid;

    @Column
    private Date createDate;

    @Column
    private String filename;

    @Column
    private boolean isImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnore
    private Post post;

    @Builder
    public FileInfo(String filename, Date createDate, boolean isImage, String uuid, Post post) {
        this.filename = filename;
        this.isImage = isImage;
        this.createDate = createDate;
        this.uuid = uuid;
        this.post = post;
    }

}
