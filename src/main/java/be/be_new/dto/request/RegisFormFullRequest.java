package be.be_new.dto.request;

import be.be_new.enums.ERegistrantType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RegisFormFullRequest {
    private Integer formId;
    private LocalDate date;
    private String status;
    private ERegistrantType registrantType;

    private RegistrantInfo registrant;
    private List<CandidateInfo> candidates;
    private Integer staffId;
    private Integer testScheduleId;

    @Data
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
    }

    @Data
    public static class CandidateInfo {
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String address;
        private LocalDate dob;
    }
}

