package ac.kr.smu.lookie.socialworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("ac.kr.smu.lookie.socialworker.repository")
@EntityScan("ac.kr.smu.lookie.socialworker.domain")
public class SocialworkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialworkerApplication.class, args);
    }

}
