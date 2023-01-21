package com.example.Image_Upload.repository;

import com.example.Image_Upload.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface uploadRepository extends JpaRepository<Images, Integer> {
}
