package com.stc.assessments.backend.repository;

import com.stc.assessments.backend.model.File;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByItemName(String name);
}
