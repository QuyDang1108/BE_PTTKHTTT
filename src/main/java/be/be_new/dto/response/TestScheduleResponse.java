package be.be_new.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TestScheduleResponse {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private String room;
    private int maxCount;
    private int registrationCount;
    private String nameTest;
    private Integer certificationId;
    private String certificationName;
}