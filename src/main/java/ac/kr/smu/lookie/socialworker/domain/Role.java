package ac.kr.smu.lookie.socialworker.domain;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private UserRole userRole;

    @Override
    public String getAuthority() {
        return userRole.toString();
    }
}
