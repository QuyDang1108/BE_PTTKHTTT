package be.be_new.entity;

import be.be_new.enums.EStaffRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String dob;
    private String email;
    private String description;
    private String password;

    @Enumerated(EnumType.STRING)
    private EStaffRole role;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private List<RegisForm> regisForms;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private List<PaymentReceipt> paymentReceipts;
}
