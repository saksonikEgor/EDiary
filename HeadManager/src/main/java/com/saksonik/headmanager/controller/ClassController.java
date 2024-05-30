package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.classes.ClassDTO;
import com.saksonik.headmanager.model.Class;
import com.saksonik.headmanager.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    @GetMapping("/{classId}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable UUID classId) {
        Class c = classService.findById(classId);
        return ResponseEntity.ok(new ClassDTO(classId, c.getName()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        return ResponseEntity.ok(classService.findAll()
                .stream()
                .map(c -> new ClassDTO(c.getClassId(), c.getName()))
                .toList());
    }
}
