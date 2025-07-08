package be.be_new.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String dor; // ngay dang ki
    private String status;

    @OneToOne(mappedBy = "regisForm")
    private PaymentReceipt paymentReceipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrant_id", nullable = false)
    private Registrant registrant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff id", nullable = false)
    private Staff staff;
}
