package io.devpl.codegen.fxui.model;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class ClockRecord {

    private LocalDate day;
    private LocalDateTime mClockOnTime;
    private LocalDateTime mClockOffTime;
    private LocalDateTime aClockOnTime;
    private LocalDateTime aClockOffTime;
    private LocalDateTime eClockOnTime;
    private LocalDateTime eClockOffTime;
    private LocalTime workOvertime;
}
