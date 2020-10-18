package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.service.CheckSuccessDeleteService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckSuccessDeleteServiceImpl implements CheckSuccessDeleteService {

    @Override
    public Map<String, Boolean> delete(JpaRepository repository, Long id) {
        Map<String, Boolean> result = new HashMap<>();

        try {
            repository.deleteById(id);
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }
}
