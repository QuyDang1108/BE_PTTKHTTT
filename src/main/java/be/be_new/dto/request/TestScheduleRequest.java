package be.be_new.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScheduleRequest {
    private Integer id; // null nếu là tạo mới
    private LocalDate date;
    private LocalTime time;
    private String room;
    private int maxCount;
    private String nameTest;
    private Integer certificationId;
}
