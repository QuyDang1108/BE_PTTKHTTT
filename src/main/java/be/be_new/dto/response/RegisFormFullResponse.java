package be.be_new.dto.response;

import be.be_new.enums.ERegistrantType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class RegisFormFullResponse {
    private Integer formId;
    private LocalDate date;
    private String status;

    private StaffInfo staff;
    private TestScheduleInfo testSchedule;
    private RegistrantInfo registrant;

    @Data
    @Builder
    public static class StaffInfo {
        private Integer id;
        private String name;
        private String email;
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

    @Data
    @Builder
    public static class RegistrantInfo {
        private Integer id;
        private String name;
        private String phone;
        private String email;
        private String address;
        private String dob;
        private String noc;
        private String aoc;
        private String poc;
        private ERegistrantType type;
        private List<CandidateInfo> candidates;
    }

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
}
