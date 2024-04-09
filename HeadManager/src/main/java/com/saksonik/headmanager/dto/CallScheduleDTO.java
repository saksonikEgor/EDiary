package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CallScheduleDTO {
    private List<CallDTO> calls;

    @AllArgsConstructor
    public static class CallDTO {
        private Integer number;
        private LocalTime start;
        private LocalTime end;
    }
}
