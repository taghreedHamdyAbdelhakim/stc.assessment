package com.stc.assessments.backend.repository;

import com.stc.assessments.backend.model.Permission;
import com.stc.assessments.backend.model.PermissionGroup;
import com.stc.assessments.backend.model.enums.PermissionLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByUserEmail(String userEmail);
    List<Permission> findAll();

    Permission findByUserEmailAndGroupAndPermissionLevel( String userEmail, PermissionGroup adminGroup, PermissionLevel permissionLevel );

}
