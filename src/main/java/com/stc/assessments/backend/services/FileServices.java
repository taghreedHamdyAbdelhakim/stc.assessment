package com.stc.assessments.backend.services;

import com.stc.assessments.backend.dto.FileMetadata;
import com.stc.assessments.backend.exception.BusinessException;
import com.stc.assessments.backend.model.File;

import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.model.Permission;
import com.stc.assessments.backend.model.PermissionGroup;
import com.stc.assessments.backend.model.enums.ItemType;
import com.stc.assessments.backend.model.enums.PermissionLevel;
import com.stc.assessments.backend.repository.FileRepository;
import com.stc.assessments.backend.repository.ItemRepository;
import com.stc.assessments.backend.repository.PermissionGroupRepository;
import com.stc.assessments.backend.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.*;
import java.util.Base64;
import java.util.List;
import javax.persistence.Query;
@Service
@Slf4j
public class FileServices {


    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PermissionGroupRepository permissionGroupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    FileRepository fileRepository;
    private final EntityManager entityManager;

    public FileServices ( EntityManager entityManager ) {
        this.entityManager = entityManager;
    }


    public File uploadFile(MultipartFile file , String parentItemName) throws IOException {
        try {
            //String fileName = StringUtils.cleanPath ( file.getOriginalFilename ( ) );

            File fileDB = new File ( );
            Item parentItem = itemRepository.findByName ( parentItemName );
            Item fileItem = new Item ( );
            fileItem.setType ( ItemType.File.name ( ) );
            fileItem.setParent ( parentItem );
            fileItem.setName ( "test2.txt" );
            fileDB.setItem ( fileItem );
            fileDB.setBinary ( file.getBytes ( ) );
            return fileRepository.save ( fileDB );
        }
        catch ( Exception e ){
            e.printStackTrace (  );
            return null;
        }
    }

    public File createFile( Long folderId, String fileName, String userEmail) throws IOException {
        if (fileName == null || fileName.isEmpty()) {
            throw new BusinessException ("File name cannot be empty.");
        }

        Item folder = itemRepository.findById(folderId).orElse(null);
        if (folder == null) {
            throw new BusinessException("Folder not found.");
        }

        PermissionGroup adminGroup = folder.getParent ().getPermissionGroup ();

        Permission editPermission = permissionRepository.findByUserEmailAndGroupAndPermissionLevel(userEmail, adminGroup, PermissionLevel.EDIT);
        if (editPermission == null) {
            throw new BusinessException ("You do not have EDIT access to this folder.");
        }

        File file = new File();
        file.setItem ( folder );
        Item fileItem = new Item ();
        fileItem.setParent ( folder );
        fileItem.setName ( fileName );
        fileItem.setType ( ItemType.File.name ( ) );
        fileItem.setPermissionGroup ( adminGroup );

        fileItem= itemRepository.save ( fileItem );

        file.setItem ( fileItem );
        InputStream in = getClass()
                .getResourceAsStream("/static/assessment.pdf");
        byte[] binaryDate = IOUtils.toByteArray ( in );
        file.setBinary ( binaryDate );
        fileRepository.save(file);

        return file;
    }

    public byte[] downloadFile(Long fileId, String userEmail) {
        File file = fileRepository.findById(fileId).orElse(null);
        if (file == null) {
            log.info ( "FileServices - downloadFile - file not found " );
            return null; // File not found
        }

        PermissionGroup adminGroup = file.getItem ( ).getPermissionGroup ( );
        log.info ( "FileServices - downloadFile - aadmineGroup:{}",adminGroup );
        Permission permission = permissionRepository.findByUserEmailAndGroupAndPermissionLevel(userEmail, adminGroup, PermissionLevel.VIEW);
        if (permission == null) {
            log.info ( "FileServices - downloadFile -user doesn't have VIEW access to this file" );
            return null;
        }

        byte[] binaryData = file.getBinary();
        log.info ( "FileServices - downloadFile - retrieve binary data of the file from the database" );
        return binaryData;


    }




    public FileMetadata getFileMetadata(Long fileId, String userEmail) {
        String sqlQuery = "SELECT i.id AS item_id, i.type, i.name, f.binary_file, pg.group_name " +
                "FROM items i " +
                "INNER JOIN files f ON i.id = f.item_id " +
                "INNER JOIN permissions p ON i.permission_group_id = p.group_id " +
                "INNER JOIN permission_groups pg ON p.group_id = pg.id " +
                "WHERE i.id = :fileId AND p.user_email = :userEmail";

        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("fileId", fileId);
        query.setParameter("userEmail", userEmail);

        List<Object[]> result = query.getResultList();
           log.info ( "result", result );
           System.out.println ( result );
        if (result.isEmpty()) {

            log.info ( "file not found or user dosen't have access" );
            return null;
        }

        Object[] row = result.get(0);

        FileMetadata fileMetadata = new FileMetadata ();
        fileMetadata.setId(Long.valueOf(row[0].toString()));
        fileMetadata.setName ( (String) row[2]);
        fileMetadata.setType ((String) row[1] );
        byte[] binaryData = (byte[]) row[3];
        fileMetadata.setBinary(binaryData);
        fileMetadata.setPermissionGroupName ( (String ) row[4]);


        return fileMetadata;
    }
}
