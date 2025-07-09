package be.be_new.services;

import be.be_new.dto.request.RegisFormFullRequest;
import be.be_new.dto.response.RegisFormFullResponse;
import be.be_new.entity.*;
import be.be_new.exception.AppException;
import be.be_new.exception.ErrorCode;
import be.be_new.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisFormService {

    private final RegisFormRepository regisFormRepository;
    private final RegistrantRepository registrantRepository;
    private final StaffRepository staffRepository;
    private final TestScheduleRepository testScheduleRepository;
    private final CandidateRepository candidateRepository;

    @Transactional
    public RegisFormFullResponse createOrUpdate(RegisFormFullRequest request) {
        // Step 1: Lấy hoặc tạo Registrant
        Registrant registrant = Optional.ofNullable(request.getRegistrant().getId())
                .flatMap(registrantRepository::findById)
                .orElseGet(Registrant::new);

        RegisFormFullRequest.RegistrantInfo regInfo = request.getRegistrant();
        registrant.setName(regInfo.getName());
        registrant.setEmail(regInfo.getEmail());
        registrant.setPhone(regInfo.getPhone());
        registrant.setAddress(regInfo.getAddress());
        registrant.setDob(regInfo.getDob());
        registrant.setNoc(regInfo.getNoc());
        registrant.setAoc(regInfo.getAoc());
        registrant.setPoc(regInfo.getPoc());
        registrant.setType(request.getRegistrantType());

        registrant = registrantRepository.save(registrant);

        // Step 2: Lấy Staff, TestSchedule
        Staff staff = staffRepository.findById(request.getStaffId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));
        TestSchedule testSchedule = testScheduleRepository.findById(request.getTestScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        // Step 3: Tạo hoặc cập nhật RegisForm
        RegisForm regisForm = Optional.ofNullable(request.getFormId())
                .flatMap(regisFormRepository::findById)
                .orElseGet(RegisForm::new);

        boolean isNew = regisForm.getId() == null;
        if (isNew) {
            if (testSchedule.getRegistrationCount() >= testSchedule.getMaxCount()) {
                throw new AppException(ErrorCode.REGISTRATION_FULL);
            }
            testSchedule.setRegistrationCount(testSchedule.getRegistrationCount() + 1);
        }

        regisForm.setDor(request.getDate());
        regisForm.setStatus(request.getStatus());
        regisForm.setRegistrant(registrant);
        regisForm.setStaff(staff);
        regisForm.setTestSchedule(testSchedule);
        regisForm = regisFormRepository.save(regisForm);

        // Step 4: Thêm Candidate và gắn RegisForm
        RegisForm finalRegisForm = regisForm;
        List<Candidate> newCandidates = request.getCandidates().stream()
                .map(c -> {
                    Candidate candidate = new Candidate();
                    candidate.setName(c.getName());
                    candidate.setEmail(c.getEmail());
                    candidate.setPhone(c.getPhone());
                    candidate.setAddress(c.getAddress());
                    candidate.setDob(c.getDob());
                    candidate.setRegisForm(finalRegisForm);
                    return candidate;
                }).toList();
        candidateRepository.saveAll(newCandidates);

        // Step 5: Tạo response
        return RegisFormFullResponse.builder()
                .formId(regisForm.getId())
                .date(regisForm.getDor())
                .status(regisForm.getStatus())
                .staff(RegisFormFullResponse.StaffInfo.builder()
                        .id(staff.getId())
                        .name(staff.getName())
                        .email(staff.getEmail())
                        .build())
                .testSchedule(RegisFormFullResponse.TestScheduleInfo.builder()
                        .id(testSchedule.getId())
                        .nameTest(testSchedule.getNameTest())
                        .date(testSchedule.getDate())
                        .time(testSchedule.getTime())
                        .room(testSchedule.getRoom())
                        .certificationName(testSchedule.getCertification().getName())
                        .build())
                .registrant(RegisFormFullResponse.RegistrantInfo.builder()
                        .id(registrant.getId())
                        .name(registrant.getName())
                        .phone(registrant.getPhone())
                        .email(registrant.getEmail())
                        .address(registrant.getAddress())
                        .dob(registrant.getDob())
                        .noc(registrant.getNoc())
                        .aoc(registrant.getAoc())
                        .poc(registrant.getPoc())
                        .type(registrant.getType())
                        .candidates(newCandidates.stream().map(c -> RegisFormFullResponse.CandidateInfo.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .email(c.getEmail())
                                .phone(c.getPhone())
                                .address(c.getAddress())
                                .dob(c.getDob())
                                .build()).toList())
                        .build())
                .build();
    }

    public RegisFormFullResponse getDetails(Integer id) {
        RegisForm form = regisFormRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        Registrant registrant = form.getRegistrant();
        List<Candidate> candidates = form.getCandidates();

        return RegisFormFullResponse.builder()
                .formId(form.getId())
                .date(form.getDor())
                .status(form.getStatus())
                .staff(RegisFormFullResponse.StaffInfo.builder()
                        .id(form.getStaff().getId())
                        .name(form.getStaff().getName())
                        .email(form.getStaff().getEmail())
                        .build())
                .testSchedule(RegisFormFullResponse.TestScheduleInfo.builder()
                        .id(form.getTestSchedule().getId())
                        .nameTest(form.getTestSchedule().getNameTest())
                        .date(form.getTestSchedule().getDate())
                        .time(form.getTestSchedule().getTime())
                        .room(form.getTestSchedule().getRoom())
                        .certificationName(form.getTestSchedule().getCertification().getName())
                        .build())
                .registrant(RegisFormFullResponse.RegistrantInfo.builder()
                        .id(registrant.getId())
                        .name(registrant.getName())
                        .phone(registrant.getPhone())
                        .email(registrant.getEmail())
                        .address(registrant.getAddress())
                        .dob(registrant.getDob())
                        .noc(registrant.getNoc())
                        .aoc(registrant.getAoc())
                        .poc(registrant.getPoc())
                        .type(registrant.getType())
                        .candidates(candidates.stream().map(c -> RegisFormFullResponse.CandidateInfo.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .email(c.getEmail())
                                .phone(c.getPhone())
                                .address(c.getAddress())
                                .dob(c.getDob())
                                .build()).toList())
                        .build())
                .build();
    }

    public List<RegisFormFullResponse> getAll() {
        List<RegisForm> regisForms = regisFormRepository.findAll();

        return regisForms.stream().map(form -> {
            Registrant registrant = form.getRegistrant();
            List<Candidate> candidates = form.getCandidates();

            return RegisFormFullResponse.builder()
                    .formId(form.getId())
                    .date(form.getDor())
                    .status(form.getStatus())
                    .staff(RegisFormFullResponse.StaffInfo.builder()
                            .id(form.getStaff().getId())
                            .name(form.getStaff().getName())
                            .email(form.getStaff().getEmail())
                            .build())
                    .testSchedule(RegisFormFullResponse.TestScheduleInfo.builder()
                            .id(form.getTestSchedule().getId())
                            .nameTest(form.getTestSchedule().getNameTest())
                            .date(form.getTestSchedule().getDate())
                            .time(form.getTestSchedule().getTime())
                            .room(form.getTestSchedule().getRoom())
                            .certificationName(form.getTestSchedule().getCertification().getName())
                            .build())
                    .registrant(RegisFormFullResponse.RegistrantInfo.builder()
                            .id(registrant.getId())
                            .name(registrant.getName())
                            .phone(registrant.getPhone())
                            .email(registrant.getEmail())
                            .address(registrant.getAddress())
                            .dob(registrant.getDob())
                            .noc(registrant.getNoc())
                            .aoc(registrant.getAoc())
                            .poc(registrant.getPoc())
                            .type(registrant.getType())
                            .candidates(candidates.stream().map(c -> RegisFormFullResponse.CandidateInfo.builder()
                                    .id(c.getId())
                                    .name(c.getName())
                                    .email(c.getEmail())
                                    .phone(c.getPhone())
                                    .address(c.getAddress())
                                    .dob(c.getDob())
                                    .build()).toList())
                            .build())
                    .build();
        }).toList();
    }
}
