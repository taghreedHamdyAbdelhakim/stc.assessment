package com.stc.assessments.backend.dto;

import com.stc.assessments.backend.model.PermissionGroup;
import com.stc.assessments.backend.model.enums.ItemType;
import lombok.Data;

import javax.persistence.*;
@Data
public class FileMetadata {

    private Long id;


    private String type;


    private String name;

    private byte[] binary;
    private String permissionGroupName;
}
