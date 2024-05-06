package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.classroom.ClassroomResponse;
import com.saksonik.headmanager.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/classroom")
@RequiredArgsConstructor
public class ClassroomController {
    private final ClassroomService classroomService;

    @GetMapping
    public List<ClassroomResponse> getAllClassrooms() {
        return classroomService.findAll()
                .stream()
                .map(c -> new ClassroomResponse(c.getClassroomId(), c.getName()))
                .toList();
    }
}
