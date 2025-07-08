package be.be_new.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TestScheduleRequest {
    private Integer id; // null nếu là tạo mới
    private LocalDate date;
    private LocalTime time;
    private String room;
    private int maxCount;
    private String nameTest;
    private Integer certificationId;
}
