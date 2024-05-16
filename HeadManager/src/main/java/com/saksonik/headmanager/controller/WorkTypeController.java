package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.workType.WorkTypeDTO;
import com.saksonik.headmanager.service.WorkTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/work-type")
@RequiredArgsConstructor
public class WorkTypeController {
    private final WorkTypeService workTypeService;

    @GetMapping("/list")
    public ResponseEntity<List<WorkTypeDTO>> getAllMarkTypes() {
        return ResponseEntity.ok(workTypeService.findAll()
                .stream()
                .map(wt -> new WorkTypeDTO(wt.getWorkTypeId(), wt.getName()))
                .toList());
    }
}
