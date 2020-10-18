package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import ac.kr.smu.lookie.socialworker.domain.Post;
import ac.kr.smu.lookie.socialworker.repository.FileRepository;
import ac.kr.smu.lookie.socialworker.service.CheckSuccessDeleteService;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService{

    @Value("${upload-primary-path}")
    private String uploadPrimaryPath;//기본 주소
    private final FileRepository fileRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final CheckSuccessDeleteService deleteService;

    @Override
    public FileInfo getFileInfo(Long id) {
        return fileRepository.getOne(id);
    }

    @Override
    public List<FileInfo> upload(List<MultipartFile> uploadFileList) {
        List<FileInfo> fileInfoList = new ArrayList<>();
        Date date = new Date();
        File uploadPath = new File(uploadPrimaryPath, sdf.format(date));

        if (!uploadPath.exists())
            uploadPath.mkdirs();//폴더가 없을 경우 폴더 생성

        for (MultipartFile multipartFile : uploadFileList) {
            String fileName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            File uploadFile = new File(uploadPath, uuid + "_" + fileName);

            try {
                multipartFile.transferTo(uploadFile);
                String contentType = Files.probeContentType(uploadFile.toPath());
                boolean isImage = false;
                if (contentType != null)
                    isImage = contentType.startsWith("image");
                FileInfo fileInfo = FileInfo.builder().createDate(date).uuid(uuid).filename(fileName).isImage(isImage).build();
                fileInfoList.add(fileInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileRepository.saveAll(fileInfoList);
    }

    @Override
    public Resource download(Long id) {
        FileInfo fileInfo = fileRepository.findById(id).get();
        String filePath = uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()) + "\\"
                + fileInfo.getUuid().trim() + "_" + fileInfo.getFilename();
        Resource downloadFile = new FileSystemResource(filePath);

        if (!downloadFile.exists())
            return null;

        return downloadFile;
    }

    @Override
    public File viewImage(Long id) {
        FileInfo fileInfo = fileRepository.findById(id).get();
        File image = new File(uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()),
                fileInfo.getUuid().trim() + "_" + fileInfo.getFilename());

        if (!image.exists() || !fileInfo.isImage())
            return null;

        return image;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        FileInfo fileInfo = fileRepository.findById(id).get();
        File deleteFile = new File(uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()), fileInfo.getUuid().trim() + "_" + fileInfo.getFilename());
        Map<String, Boolean> result;

        try{
            deleteFile.delete();
            result = deleteService.delete(fileRepository,id);
            log.info(id+"번 파일 삭제 성공");
        }catch (Exception e){
            result = new HashMap<>();
            result.put("success",false);
            log.info(id+"번 파일 삭제 실패");
        }

        return result;
    }

    @Override
    public void deleteByPost(Post post) {
        fileRepository.deleteByPost(post);
    }
}
