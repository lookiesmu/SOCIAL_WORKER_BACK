package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import ac.kr.smu.lookie.socialworker.domain.Post;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileService {

    public FileInfo getFileInfo(Long id);

    public List<FileInfo> upload(List<MultipartFile> uploadFileList);

    public Resource download(Long id);

    public File viewImage(Long id);

    public Map<String, Boolean> delete(Long id);

    public void deleteByPost(Post post);
}
