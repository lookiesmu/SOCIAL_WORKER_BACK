package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import ac.kr.smu.lookie.socialworker.repository.FileRepository;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${upload-primary-path}")
    private String uploadPrimaryPath;//기본 주소
    private final FileRepository fileRepository;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
    public Resource download(Long fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId).get();
        String filePath = uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()) + "\\" + fileInfo.getUuid().trim() + "_" + fileInfo.getFilename();
        Resource downloadFile = new FileSystemResource(filePath);

        if (!downloadFile.exists())
            return null;

        return downloadFile;
    }

    @Override
    public File viewImage(Long fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId).get();
        File image = new File(uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()), fileInfo.getUuid().trim() + "_" + fileInfo.getFilename());

        if (!image.exists() || !fileInfo.isImage())
            return null;

        return image;
    }

    @Override
    public void delete(Long fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId).get();
        File deleteFile = new File(uploadPrimaryPath + "\\" + sdf.format(fileInfo.getCreateDate()), fileInfo.getUuid().trim() + "_" + fileInfo.getFilename());

        deleteFile.delete();
        fileRepository.deleteById(fileId);
    }
}
