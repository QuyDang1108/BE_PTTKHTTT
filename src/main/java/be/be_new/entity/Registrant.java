package be.be_new.entity;

import be.be_new.enums.ERegistrantType;
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
public class Registrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrant_id")
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String dob;

    private String noc; // Tên đơn vị
    private String aoc; // Địa chỉ đơn vị
    private String poc; // Số điện thoại đơn vị

    @Enumerated(EnumType.STRING)
    private ERegistrantType type;

    @OneToMany(mappedBy = "registrant", fetch = FetchType.LAZY)
    private List<RegisForm> regisForms;

    @OneToMany(mappedBy = "registrant", fetch = FetchType.LAZY)
    private List<Candidate> candidates;
}
