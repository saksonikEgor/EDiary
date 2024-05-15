package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.markType.MarkTypeDTO;
import com.saksonik.headmanager.service.MarkTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/mark-type")
@RequiredArgsConstructor
public class MarkTypeController {
    private final MarkTypeService markTypeService;

    @GetMapping("/list")
    public ResponseEntity<List<MarkTypeDTO>> getAllMarkTypes() {
        return ResponseEntity.ok(markTypeService.findAll()
                .stream()
                .map(mt -> new MarkTypeDTO(mt.getMarkTypeId(), mt.getType()))
                .toList());
    }
}
