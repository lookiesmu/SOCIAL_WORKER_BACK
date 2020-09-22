package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable("fileId") Long fileId){
        Resource file = fileService.download(fileId);
        HttpHeaders headers = new HttpHeaders();

        if (file == null)//파일이 없을 경우
            return ResponseEntity.notFound().build();

        String filename = file.getFilename();
        filename = filename.substring(filename.indexOf("_") + 1);
        try {
            headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename,"UTF-8"));
        }catch (UnsupportedEncodingException e){return ResponseEntity.status(500).build();}

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<?> viewImage(@PathVariable("fileId") Long fileId){//이미지 조회
        java.io.File image= fileService.viewImage(fileId);
        HttpHeaders headers = new HttpHeaders();

        if(image==null)//파일이 없을 경우
            return ResponseEntity.notFound().build();

        try {
            headers.add("Content-Type", Files.probeContentType(image.toPath()));
            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(image), headers, HttpStatus.OK);
        }catch (IOException i){return ResponseEntity.status(500).build();}

    }

    @PostMapping
    public ResponseEntity<?> postFile(List<MultipartFile> uploadFileList) {
        List<FileInfo> uploadFileInfo = fileService.upload(uploadFileList);
        CollectionModel<FileInfo> body = CollectionModel.of(uploadFileInfo);

        body.add(linkTo(methodOn(FileController.class).postFile(uploadFileList)).withSelfRel());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileId") Long fileId){
        fileService.delete(fileId);
        return ResponseEntity.noContent().build();
    }
}
