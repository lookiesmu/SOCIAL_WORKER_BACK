package ac.kr.smu.lookie.socialworker.repository;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileInfo, Long> {
}
