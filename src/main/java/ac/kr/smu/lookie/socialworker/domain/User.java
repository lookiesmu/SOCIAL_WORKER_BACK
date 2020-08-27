package ac.kr.smu.lookie.socialworker.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Getter
@Builder
public class User implements UserDetails {

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;  //비밀번호

    @Column
    private String location; //지역

    @Column
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int anonymity; //익명여부

    @Column
    private int point; //포인트

    @Column
//    private UserRole role; //권한
    private Collection<Role> role; //더 찾아보기

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //계정이 갖고있는 권한 목록 리턴
//        List<UserRole> roles = new ArrayList<>();
        return role;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() { //계정 만료되어있는지 확인
        return true; //만료 안됨
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() { //계정 잠겨있는지 학인
        return true; //잠기지 않음
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() { //비밀번호 만료 여부 확인
        return true; //만료 안됨
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() { //계정 활성화 상태인지 확인
        return true; //활성화
    }
}
