package com.stc.assessments.backend.repository;


import com.stc.assessments.backend.model.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionGroupRepository  extends JpaRepository<PermissionGroup, Long> {

    PermissionGroup findByGroupName(String groupName);

}
