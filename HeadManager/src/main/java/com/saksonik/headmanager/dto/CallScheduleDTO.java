package com.saksonik.headmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CallScheduleDTO {
    private List<CallDTO> calls;

    @AllArgsConstructor
    @Getter
    public static class CallDTO {
        private Integer number;
        private LocalTime start;
        private LocalTime end;
    }
}
