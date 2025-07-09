package be.be_new.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "regis_form_id")
    private Integer id;
    private LocalDate dor; // ngay dang ki
    private String status;

    @OneToOne(mappedBy = "regisForm")
    private PaymentReceipt paymentReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrant_id", nullable = false)
    private Registrant registrant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff id", nullable = false)
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private TestSchedule testSchedule;

    @OneToMany(mappedBy = "regisForm", fetch = FetchType.LAZY)
    private List<Candidate> candidates;
}
