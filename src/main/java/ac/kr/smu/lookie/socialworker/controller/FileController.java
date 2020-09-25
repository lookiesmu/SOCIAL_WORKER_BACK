package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.FileInfo;
import ac.kr.smu.lookie.socialworker.domain.User;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable("fileId") Long fileId, @AuthenticationPrincipal User user) {//파일 다운
        Resource file = fileService.download(fileId);
        HttpHeaders headers = new HttpHeaders();

        if (file == null) {//파일이 없을 경우
            log.info(user.getUsername() + " : " + fileId + "다운로드 실패");
            return ResponseEntity.notFound().build();
        }

        String filename = file.getFilename();
        filename = filename.substring(filename.indexOf("_") + 1);
        try {
            headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(500).build();
        }
        log.info(user.getUsername() + " : " + fileId + "다운로드 성공");
        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<?> viewImage(@PathVariable("fileId") Long fileId, @AuthenticationPrincipal User user) {//이미지 조회
        File image = fileService.viewImage(fileId);
        HttpHeaders headers = new HttpHeaders();

        if (image == null) {//파일이 없을 경우
            log.info(user.getUsername() + " : " + fileId + " 조회 실패");
            return ResponseEntity.notFound().build();
        }

        try {
            headers.add("Content-Type", Files.probeContentType(image.toPath()));
            log.info(user.getUsername() + " : " + fileId + " 조회 성공");
            return new ResponseEntity<>(FileCopyUtils.copyToByteArray(image), headers, HttpStatus.OK);
        } catch (IOException i) {
            return ResponseEntity.status(500).build();
        }

    }

    @PostMapping
    public ResponseEntity<?> postFile(List<MultipartFile> uploadFileList, @AuthenticationPrincipal User user) {//파일 업로드
        List<FileInfo> uploadFileInfo = fileService.upload(uploadFileList);
        CollectionModel<FileInfo> body = CollectionModel.of(uploadFileInfo);

        body.add(linkTo(methodOn(FileController.class).postFile(uploadFileList, user)).withSelfRel());
        log.info(user.getUsername() + " : " + "파일 업로드");
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable("fileId") Long fileId, @AuthenticationPrincipal User user) {
        Map<String, Boolean> body = new HashMap<>();

        try {
            fileService.delete(fileId);
            body.put("success", true);
        } catch (Exception e) {
            body.put("success", null);
            log.info(user.getUsername() + " : " + fileId + " 파일 삭제 실패");
        }

        EntityModel<Map<String, Boolean>> returnBody = EntityModel.of(body);
        returnBody.add(linkTo(methodOn(FileController.class).deleteFile(fileId, user)).withSelfRel());
        log.info(user.getUsername() + " : " + fileId + " 파일 삭제 성공");
        return ResponseEntity.ok(returnBody);
    }
}
