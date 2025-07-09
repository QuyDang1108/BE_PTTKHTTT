package be.be_new.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ExaminationPermitResponse {
    private Integer permitId;
    private LocalDate doc;

    private CandidateInfo candidate;
    private TestScheduleInfo testSchedule;

    @Data
    @Builder
    public static class CandidateInfo {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private LocalDate dob;
    }

    @Data
    @Builder
    public static class TestScheduleInfo {
        private Integer id;
        private String nameTest;
        private LocalDate date;
        private LocalTime time;
        private String room;
        private String certificationName;
    }
}
