package ac.kr.smu.lookie.socialworker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private boolean anonymity;//익명 게시판 여부

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;//게시판 제한(복지사만 접근 가능한 게시판 or 다른 유저들도 가능한 게시판)

    @Column
    @JsonIgnore
    private boolean permit;//승인 여부

    public void setPermit(boolean permit){
        this.permit=permit;
    }
}
