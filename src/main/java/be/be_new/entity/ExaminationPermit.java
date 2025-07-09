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
public class ExaminationPermit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permit_id")
    private Integer id;
    private LocalDate doc; // Ngày tạo

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private TestSchedule testSchedule;
}
