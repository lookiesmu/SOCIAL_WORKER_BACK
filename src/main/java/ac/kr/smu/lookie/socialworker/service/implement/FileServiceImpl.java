package ac.kr.smu.lookie.socialworker.service.implement;

import ac.kr.smu.lookie.socialworker.domain.File;
import ac.kr.smu.lookie.socialworker.repository.FileRepository;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public List<File> upload(List<MultipartFile> uploadFileList){
        List<File> fileList = new ArrayList<>();

        Date date = new Date();
        java.io.File uploadPath = new java.io.File(uploadPrimaryPath, sdf.format(date));

        if(!uploadPath.exists())
            uploadPath.mkdirs();//폴더가 없을 경우 폴더 생성

        for(MultipartFile multipartFile : uploadFileList){
            String fileName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            java.io.File uploadFile = new java.io.File(uploadPath, uuid+"_"+fileName);

            try {
                multipartFile.transferTo(uploadFile);
                String contentType = Files.probeContentType(uploadFile.toPath());
                boolean isImage = false;
                if(contentType!=null)
                    isImage = contentType.startsWith("image");
                File file = File.builder().createDate(date).uuid(uuid).filename(fileName).isImage(isImage).build();
                fileList.add(file);
            }catch (IOException e){e.printStackTrace();}
        }

        return fileRepository.saveAll(fileList);
    }

    @Override
    public Resource download(Long fileId){
        File file = fileRepository.findById(fileId).get();
        String filePath= uploadPrimaryPath+"\\"+sdf.format(file.getCreateDate())+"\\"+file.getUuid().trim()+"_"+file.getFilename();
        Resource downloadFile = new FileSystemResource(filePath);
        if(!downloadFile.exists())
            return null;
        return downloadFile;
    }
}
