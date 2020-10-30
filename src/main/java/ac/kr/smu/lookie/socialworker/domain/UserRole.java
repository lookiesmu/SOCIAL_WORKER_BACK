package ac.kr.smu.lookie.socialworker.domain;

import javax.persistence.Embeddable;

@Embeddable
public enum  UserRole {
    SOCIALWORKER, USER, CENTER, ADMIN
}
