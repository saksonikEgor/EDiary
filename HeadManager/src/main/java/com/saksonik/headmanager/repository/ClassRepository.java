package com.saksonik.headmanager.repository;

import com.saksonik.headmanager.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClassRepository extends JpaRepository<Class, UUID> {
}