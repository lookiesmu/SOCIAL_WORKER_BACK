package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public List<File> upload(List<MultipartFile> uploadFileList);

    public Resource download(Long fileId);

    public java.io.File viewImage(Long fileId);

    public void delete(Long fileId);
}
