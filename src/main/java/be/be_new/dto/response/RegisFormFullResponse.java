package be.be_new.dto.response;

import be.be_new.enums.ERegistrantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisFormFullResponse {
    private Integer formId;
    private LocalDate date;
    private String status;

    private StaffInfo staff;
    private TestScheduleInfo testSchedule;
    private List<CandidateInfo> candidates;
    private RegistrantInfo registrant;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StaffInfo {
        private Integer id;
        private String name;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestScheduleInfo {
        private Integer id;
        private String nameTest;
        private LocalDate date;
        private LocalTime time;
        private String room;
        private String certificationName;
        private int certPrice;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
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
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CandidateInfo {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private LocalDate dob;
    }
}
