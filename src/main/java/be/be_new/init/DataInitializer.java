package be.be_new.init;

import be.be_new.entity.*;
import be.be_new.enums.EPaymentType;
import be.be_new.enums.ERegistrantType;
import be.be_new.enums.EStaffRole;
import be.be_new.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RegistrantRepository registrantRepo;
    private final CandidateRepository candidateRepo;
    private final CertificationRepository certificationRepo;
    private final TestScheduleRepository testScheduleRepo;
    private final ExaminationPermitRepository examPermitRepo;
    private final RegisFormRepository regisFormRepo;
    private final PaymentReceiptRepository paymentReceiptRepo;
    private final StaffRepository staffRepo;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            // Chỉ khởi tạo khi chưa có nhân viên (staff)
            if (candidateRepo.count() > 0) {
                System.out.println("Data already exists. Skipping initialization.");
                return;
            }

            // === 1. Registrants ===
            Registrant reg1 = new Registrant();
            reg1.setName("Nguyen Van A");
            reg1.setPhone("0912345678");
            reg1.setEmail("a@gmail.com");
            reg1.setAddress("Hanoi");
            reg1.setDob("1990-01-01");
            reg1.setNoc("Trường ĐH A");
            reg1.setAoc("Hà Nội");
            reg1.setPoc("0245678910");
            reg1.setType(ERegistrantType.DON_VI);

            Registrant reg2 = new Registrant();
            reg2.setName("Le Thi B");
            reg2.setPhone("0987654321");
            reg2.setEmail("b@gmail.com");
            reg2.setAddress("HCMC");
            reg2.setDob("1995-05-10");
            reg2.setNoc("Tự do");
            reg2.setAoc("Tp.HCM");
            reg2.setPoc("0287654321");
            reg2.setType(ERegistrantType.TU_DO);

            registrantRepo.saveAll(List.of(reg1, reg2));

            // === 2. Certification ===
            Certification cert1 = new Certification();
            cert1.setName("MOS Word");

            Certification cert2 = new Certification();
            cert2.setName("MOS Excel");

            certificationRepo.saveAll(List.of(cert1, cert2));

            // === 3. TestSchedule ===
            TestSchedule ts1 = new TestSchedule();
            ts1.setDate(LocalDate.of(2025, 8, 15)); // hoặc LocalDate.parse("2025-08-15")
            ts1.setTime(LocalTime.of(8, 30));
            ts1.setRoom("A101");
            ts1.setMaxCount(30);
            ts1.setRegistrationCount(0);
            ts1.setNameTest("Ca 1");
            ts1.setCertification(cert1);

            TestSchedule ts2 = new TestSchedule();
            ts1.setDate(LocalDate.of(2025, 8, 16)); // hoặc LocalDate.parse("2025-08-15")
            ts1.setTime(LocalTime.of(10, 30));
            ts2.setRoom("B202");
            ts2.setMaxCount(25);
            ts2.setRegistrationCount(0);
            ts2.setNameTest("Ca 2");
            ts2.setCertification(cert2);

            testScheduleRepo.saveAll(List.of(ts1, ts2));

            // === 4. Staff ===
            Staff staff1 = new Staff();
            staff1.setName("Nguyen Staff");
            staff1.setPhone("0901234567");
            staff1.setAddress("Can Tho");
            staff1.setDob("1992-12-01");
            staff1.setEmail("staff1@org.com");
            staff1.setPassword(passwordEncoder.encode("123456"));
            staff1.setRole(EStaffRole.KE_TOAN);
            staff1.setDescription("Kế toán phụ trách thanh toán");

            Staff staff2 = new Staff();
            staff2.setName("Le Tiep Nhan");
            staff2.setPhone("0908888777");
            staff2.setAddress("Da Nang");
            staff2.setDob("1993-03-10");
            staff2.setEmail("staff2@org.com");
            staff2.setPassword(passwordEncoder.encode("123456"));
            staff2.setRole(EStaffRole.TIEP_NHAN);
            staff2.setDescription("Tiếp nhận hồ sơ");

            Staff staff3 = new Staff();
            staff3.setName("Tran Nhap Lieu");
            staff3.setPhone("0906666555");
            staff3.setAddress("Hai Phong");
            staff3.setDob("1991-04-20");
            staff3.setEmail("staff3@org.com");
            staff3.setPassword(passwordEncoder.encode("123456"));
            staff3.setRole(EStaffRole.NHAP_LIEU);
            staff3.setDescription("Nhập dữ liệu hệ thống");

            Staff staff4 = new Staff();
            staff4.setName("Pham Quan Ly");
            staff4.setPhone("0904444333");
            staff4.setAddress("Ha Noi");
            staff4.setDob("1990-09-09");
            staff4.setEmail("staff4@org.com");
            staff4.setPassword(passwordEncoder.encode("123456"));
            staff4.setRole(EStaffRole.QUAN_LY);
            staff4.setDescription("Quản lý hệ thống");

            staffRepo.saveAll(List.of(staff1, staff2, staff3, staff4));

            // === 5. RegisForm ===
            RegisForm rf1 = new RegisForm();
            rf1.setDor("2025-07-01");
            rf1.setStatus("Đã xác nhận");
            rf1.setRegistrant(reg1);
            rf1.setStaff(staff2); // Nhân viên tiếp nhận

            RegisForm rf2 = new RegisForm();
            rf2.setDor("2025-07-02");
            rf2.setStatus("Đang xử lý");
            rf2.setRegistrant(reg2);
            rf2.setStaff(staff2); // Nhân viên tiếp nhận

            regisFormRepo.saveAll(List.of(rf1, rf2));

            // === 6. PaymentReceipt ===
            PaymentReceipt pr1 = new PaymentReceipt();
            pr1.setMoney(500000);
            pr1.setDop("2025-07-02");
            pr1.setStatus("Đã thanh toán");
            pr1.setPaymentType(EPaymentType.CK);
            pr1.setRegisForm(rf1);
            pr1.setStaff(staff1); // Nhân viên kế toán

            PaymentReceipt pr2 = new PaymentReceipt();
            pr2.setMoney(600000);
            pr2.setDop("2025-07-03");
            pr2.setStatus("Chờ xác nhận");
            pr2.setPaymentType(EPaymentType.TT);
            pr2.setRegisForm(rf2);
            pr2.setStaff(staff1); // Nhân viên kế toán

            paymentReceiptRepo.saveAll(List.of(pr1, pr2));

            // === 7. Candidate ===
            Candidate cand1 = new Candidate();
            cand1.setName("Tran Van C");
            cand1.setPhone("0911223344");
            cand1.setEmail("c@gmail.com");
            cand1.setAddress("Hue");
            cand1.setDob(LocalDate.of(2000, 3, 15));
            cand1.setRegistrant(reg1);

            Candidate cand2 = new Candidate();
            cand2.setName("Pham Thi D");
            cand2.setPhone("0922334455");
            cand2.setEmail("d@gmail.com");
            cand2.setAddress("Da Nang");
            cand2.setDob(LocalDate.of(2001, 6, 20));
            cand2.setRegistrant(reg2);

            candidateRepo.saveAll(List.of(cand1, cand2));

            // === 8. ExaminationPermit ===
            ExaminationPermit ep1 = new ExaminationPermit();
            ep1.setDoc("2025-07-05");
            ep1.setCandidate(cand1);
            ep1.setTestSchedule(ts1);

            ExaminationPermit ep2 = new ExaminationPermit();
            ep2.setDoc("2025-07-06");
            ep2.setCandidate(cand2);
            ep2.setTestSchedule(ts2);

            examPermitRepo.saveAll(List.of(ep1, ep2));

            // Gán permit cho candidate (liên kết 2 chiều)
            cand1.setExaminationPermit(ep1);
            cand2.setExaminationPermit(ep2);
            candidateRepo.saveAll(List.of(cand1, cand2));
        };
    }
}
