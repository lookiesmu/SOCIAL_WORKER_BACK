package ac.kr.smu.lookie.socialworker.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name; //이름

    @Column
    private String nickname; //닉네임

    @Column
    private String email; //이메일

    @Column
    private String username; //아이디

    @Column
    private String password;  //비밀번호

    @Column
    private String location; //지역

    @Column
    private UserRole role; //권한

    @Column
    private int anonymity; //익명여부

    @Column
    private int point; //포인트
}
