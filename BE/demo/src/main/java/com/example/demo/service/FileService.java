package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {

    List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException;

    Path downloadFile(String filename) throws IOException;

}
