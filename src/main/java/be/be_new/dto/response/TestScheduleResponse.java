package be.be_new.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private int certPrice;
}