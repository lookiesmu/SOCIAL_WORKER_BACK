package ac.kr.smu.lookie.socialworker.service;

import ac.kr.smu.lookie.socialworker.domain.File;
import ac.kr.smu.lookie.socialworker.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${upload-location}")
    private String uploadLocation;
    private final FileRepository fileRepository;

    public List<File> upload(List<MultipartFile> uploadFileList){
        List<File> fileList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        java.io.File uploadPath = new java.io.File(uploadLocation, sdf.format(date));
        log.info(uploadLocation);
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

}
