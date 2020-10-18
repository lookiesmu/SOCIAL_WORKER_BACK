package ac.kr.smu.lookie.socialworker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

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
