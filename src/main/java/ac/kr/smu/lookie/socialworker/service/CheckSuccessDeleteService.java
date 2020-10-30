package ac.kr.smu.lookie.socialworker.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface CheckSuccessDeleteService {

    public Map<String, Boolean> delete(JpaRepository repository, Long id);
}