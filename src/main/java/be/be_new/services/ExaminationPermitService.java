package be.be_new.services;

import be.be_new.dto.request.CandidateRequest;
import be.be_new.dto.response.ExaminationPermitResponse;
import be.be_new.entity.Candidate;
import be.be_new.entity.ExaminationPermit;
import be.be_new.entity.RegisForm;
import be.be_new.entity.TestSchedule;
import be.be_new.exception.AppException;
import be.be_new.exception.ErrorCode;
import be.be_new.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationPermitService {

    private final RegisFormRepository regisFormRepository;
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final ExaminationPermitRepository examinationPermitRepository;
    private final CandidateRepository candidateRepository;
    private final TestScheduleRepository testScheduleRepository;

    @Transactional
    public List<ExaminationPermitResponse> createFromRegisForm(Integer regisformId) {
        RegisForm form = regisFormRepository.findById(regisformId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        // Kiểm tra thanh toán qua regisFormId
        boolean paid = paymentReceiptRepository
                .findById(form.getId())
                .stream()
                .anyMatch(receipt -> "Đã thanh toán".equalsIgnoreCase(receipt.getStatus()));
        if (!paid) {
            throw new AppException(ErrorCode.PAYMENT_NOT_COMPLETED);
        }

        List<Candidate> candidates = form.getCandidates();
        List<ExaminationPermit> savedPermits = new ArrayList<>();

        for (Candidate c : candidates) {
            if (c.getExaminationPermit() == null) {
                ExaminationPermit permit = ExaminationPermit.builder()
                        .candidate(c)
                        .testSchedule(form.getTestSchedule())
                        .doc(LocalDate.now())
                        .build();

                ExaminationPermit saved = examinationPermitRepository.save(permit);
                c.setExaminationPermit(saved); // Gắn ngược lại nếu cần
                savedPermits.add(saved);
            }
        }

        return savedPermits.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExaminationPermitResponse mapToResponse(ExaminationPermit permit) {
        Candidate c = permit.getCandidate();
        TestSchedule t = permit.getTestSchedule();

        return ExaminationPermitResponse.builder()
                .permitId(permit.getId())
                .doc(permit.getDoc())
                .candidate(ExaminationPermitResponse.CandidateInfo.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .email(c.getEmail())
                        .phone(c.getPhone())
                        .address(c.getAddress())
                        .dob(c.getDob())
                        .build())
                .testSchedule(ExaminationPermitResponse.TestScheduleInfo.builder()
                        .id(t.getId())
                        .nameTest(t.getNameTest())
                        .date(t.getDate())
                        .time(t.getTime())
                        .room(t.getRoom())
                        .certificationName(t.getCertification().getName())
                        .build())
                .build();
    }

    public List<ExaminationPermitResponse> getAllPermits() {
        return examinationPermitRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ExaminationPermitResponse getPermitDetails(Integer id) {
        ExaminationPermit permit = examinationPermitRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));
        return mapToResponse(permit);
    }

    public Integer createExaminationPermitBasedOnCandidates(List<CandidateRequest> requests) {
        int createdCount = 0;
        for (CandidateRequest request : requests) {
            RegisForm regisForm = regisFormRepository.getById(request.getRegisId());
            if (regisForm == null) {
                throw new AppException(ErrorCode.NOT_FOUND_DATA);
            }

            Candidate candidate = candidateRepository.getById(request.getId());
            if (candidate == null) {
                throw new AppException(ErrorCode.NOT_FOUND_DATA);
            }

            ExaminationPermit examinationPermit = new ExaminationPermit();
            examinationPermit.setCandidate(candidate);
            examinationPermit.setDoc(LocalDate.now());
            examinationPermit.setTestSchedule(regisForm.getTestSchedule());

            ExaminationPermit examinationPermit1 = examinationPermitRepository.save(examinationPermit); // Lưu permit
            Candidate savedCandidate = candidateRepository.getById(candidate.getId());
            savedCandidate.setExaminationPermit(examinationPermit1);
            candidateRepository.save(savedCandidate);
            createdCount++; // Tăng đếm
        }

        return createdCount;
    }

}

