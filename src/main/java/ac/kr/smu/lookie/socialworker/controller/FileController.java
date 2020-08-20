package ac.kr.smu.lookie.socialworker.controller;

import ac.kr.smu.lookie.socialworker.domain.File;
import ac.kr.smu.lookie.socialworker.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable("fileId") Long fileId) throws IOException {
        Resource file = fileService.download(fileId);
        HttpHeaders headers = new HttpHeaders();

        if (file == null)//파일이 없을 경우
            return ResponseEntity.notFound().build();

        String filename = file.getFilename();
        filename = filename.substring(filename.indexOf("_") + 1);
        headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename,"UTF-8"));

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postFile(List<MultipartFile> uploadFileList) {
        List<File> uploadFile = fileService.upload(uploadFileList);
        return new ResponseEntity<>(uploadFile, HttpStatus.OK);
    }


}
