package be.be_new.entity;

import be.be_new.enums.EPaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_receipt_id")
    private Integer id;
    private float money;
    private String dop;// ngày thanh toán
    private String status;

    @Enumerated(EnumType.STRING)
    private EPaymentType paymentType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regis_form_id", nullable = false)
    private RegisForm regisForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff id", nullable = false)
    private Staff staff;
}
