package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileService {

    public List<FileInfo> upload(List<MultipartFile> uploadFileList);

    public Resource download(Long fileId);

    public File viewImage(Long fileId);

    public void delete(Long fileId);
}
