package com.stc.assessments.backend.controller;


import com.stc.assessments.backend.dto.FileMetadata;
import com.stc.assessments.backend.services.FileServices;
import com.stc.assessments.backend.model.File;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;


@Slf4j
@RequestMapping
@RestController
public class FileController {


    @Autowired
    FileServices fileServices;

   // http://localhost:8080/files/1/download?userEmail=taghreed@gmail.com
    @GetMapping(value = "files/{fileId}/download" , produces = "application/pdf")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId, @RequestParam String userEmail) {

        byte[] fileContent = fileServices.downloadFile(fileId, userEmail);

        if (fileContent == null) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body(null);
        }
        String file = toText(getClass().getResourceAsStream("/static/assessment.pdf"));
        return ResponseEntity.ok(file);
    }
    // http://localhost:8080/files/1/fileMetadata?userEmail=taghreed@gmail.com
    @GetMapping(value = "files/{fileId}/fileMetadata" )
    public ResponseEntity<FileMetadata> getFileMetadata(@PathVariable Long fileId, @RequestParam String userEmail) {

        FileMetadata fileMetadata = fileServices.getFileMetadata ( fileId , userEmail );

        if (fileMetadata == null) {
            return ResponseEntity.status( HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(fileMetadata);
    }
    //http://localhost:8080/folders/2/files?fileName=assessment.pdf&userName=admin@stc.com
    @PostMapping("folders/{folderId}/files")
    public ResponseEntity<File> createFile(@PathVariable Long folderId, @RequestParam String fileName , @RequestParam String userName) throws IOException {
        File createdFile = fileServices.createFile(folderId, fileName , userName);
        return ResponseEntity.ok( createdFile);
    }
    private  String toText( InputStream in) {
        return new BufferedReader ( new InputStreamReader (in, UTF_8))
                .lines().collect( Collectors.joining("\n"));
    }
}
