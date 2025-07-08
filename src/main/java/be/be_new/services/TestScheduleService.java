package be.be_new.services;

import be.be_new.dto.request.TestScheduleRequest;
import be.be_new.dto.response.TestScheduleResponse;
import be.be_new.entity.Certification;
import be.be_new.entity.TestSchedule;
import be.be_new.exception.AppException;
import be.be_new.exception.ErrorCode;
import be.be_new.repository.CertificationRepository;
import be.be_new.repository.TestScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestScheduleService {

    private final TestScheduleRepository testScheduleRepository;
    private final CertificationRepository certificationRepository;

    public TestScheduleResponse saveOrUpdate(TestScheduleRequest dto) {
        Certification certification = certificationRepository.findById(dto.getCertificationId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));

        TestSchedule schedule;

        if (dto.getId() != null) {
            schedule = testScheduleRepository.findById(dto.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));
        } else {
            schedule = new TestSchedule();
            schedule.setRegistrationCount(0);
        }

        schedule.setDate(dto.getDate());
        schedule.setTime(dto.getTime());
        schedule.setRoom(dto.getRoom());
        schedule.setMaxCount(dto.getMaxCount());
        schedule.setNameTest(dto.getNameTest());
        schedule.setCertification(certification);

        TestSchedule saved = testScheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public void increaseRegistrationCount(Integer testScheduleId) {
        TestSchedule schedule = testScheduleRepository.findById(testScheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_DATA));
        if (schedule.getRegistrationCount() >= schedule.getMaxCount()) {
            throw new AppException(ErrorCode.USER_EXISTED); // hoặc REGISTRATION_FULL như gợi ý trước
        }
        schedule.setRegistrationCount(schedule.getRegistrationCount() + 1);
    }

    public List<TestScheduleResponse> findAll() {
        return testScheduleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private TestScheduleResponse toResponse(TestSchedule schedule) {
        return TestScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .time(schedule.getTime())
                .room(schedule.getRoom())
                .maxCount(schedule.getMaxCount())
                .registrationCount(schedule.getRegistrationCount())
                .nameTest(schedule.getNameTest())
                .certificationId(schedule.getCertification().getId())
                .certificationName(schedule.getCertification().getName())
                .build();
    }
}