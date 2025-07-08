package be.be_new.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private String room;
    private int registrationCount;
    private int maxCount;
    private String nameTest;

    @OneToMany(mappedBy = "testSchedule", fetch = FetchType.LAZY)
    private List<ExaminationPermit> examinationPermits;

    @OneToMany(mappedBy = "testSchedule", fetch = FetchType.LAZY)
    private List<RegisForm> regisForms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id", nullable = false)
    private Certification certification;
}

