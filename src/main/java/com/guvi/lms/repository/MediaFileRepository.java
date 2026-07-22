package com.guvi.lms.repository;

import com.guvi.lms.entity.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository
        extends JpaRepository<MediaFile, Long> {
}