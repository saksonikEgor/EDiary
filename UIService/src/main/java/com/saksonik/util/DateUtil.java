package com.saksonik.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    private DateUtil() {
    }

    public static List<LocalDate> buildPeriod(LocalDate start, LocalDate end) {
        List<LocalDate> period = new ArrayList<>();
        LocalDate currentDate = start;

        while (!currentDate.isAfter(end)) {
            period.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return period;
    }
}
