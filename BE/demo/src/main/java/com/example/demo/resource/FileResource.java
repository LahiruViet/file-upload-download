package com.example.demo.resource;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequestMapping("/api/v1/file")
public class FileResource {


    @Autowired
    private FileService fileService;

    // Define a method to upload files
    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(
            @RequestParam("files")List<MultipartFile> multipartFiles) throws IOException {

        return ResponseEntity.ok().body(fileService.uploadFiles(multipartFiles));
    }

    // Define a method to download files
    @GetMapping("download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename)
            throws IOException {

        Path filePath = fileService.downloadFile(filename);
        Resource resource = new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders)
                .body(resource);
    }

}
