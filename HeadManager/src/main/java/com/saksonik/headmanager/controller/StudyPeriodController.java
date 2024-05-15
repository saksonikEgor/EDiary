package com.saksonik.headmanager.controller;

import com.saksonik.headmanager.dto.srudyPeriod.StudyPeriodDTO;
import com.saksonik.headmanager.model.StudyPeriod;
import com.saksonik.headmanager.service.StudyPeriodService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/study-period")
@RequiredArgsConstructor
public class StudyPeriodController {
    private final StudyPeriodService studyPeriodService;

    @GetMapping("/list")
    public ResponseEntity<List<StudyPeriodDTO>> getStudyPeriods() {
        return ResponseEntity.ok(studyPeriodService.findAll()
                .stream()
                .map(sp -> new StudyPeriodDTO(sp.getStudyPeriodId(), sp.getName()))
                .toList());
    }
}
