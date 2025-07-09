package be.be_new.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regis_form_id", nullable = false)
    private RegisForm regisForm;

    @OneToOne(fetch = FetchType.LAZY)
    private ExaminationPermit examinationPermit;
}
