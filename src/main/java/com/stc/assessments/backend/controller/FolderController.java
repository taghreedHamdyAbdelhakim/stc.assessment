package com.stc.assessments.backend.controller;

import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.services.ItemServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces/{spaceId}/folders")
@Slf4j
public class FolderController {
    @Autowired
    private ItemServices itemServices;
//http://localhost:8080/spaces/1/folders?folderName=backend&userEmail=admin@stc.com
    @PostMapping
    public ResponseEntity < Item > createFolder( @PathVariable Long spaceId, @RequestParam String folderName , @RequestParam String userEmail) {
         Item createdFolder = itemServices.createFolder(spaceId, folderName , userEmail);
         log.info ("Folder created with folderID:{} " , createdFolder.getId()  );
        return ResponseEntity.ok(createdFolder);
    }
}
