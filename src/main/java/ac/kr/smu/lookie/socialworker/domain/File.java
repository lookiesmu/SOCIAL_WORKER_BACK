package ac.kr.smu.lookie.socialworker.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class File {

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

    @Builder
    public File(String filename, Date createDate, boolean isImage, String uuid) {
        this.filename = filename;
        this.isImage = isImage;
        this.createDate = createDate;
        this.uuid = uuid;
    }

}
