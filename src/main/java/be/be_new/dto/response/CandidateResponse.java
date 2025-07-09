package be.be_new.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private LocalDate dob;
    private Integer regisId;
    private Integer permitId;
}
