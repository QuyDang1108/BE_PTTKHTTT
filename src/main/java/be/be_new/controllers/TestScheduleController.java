package be.be_new.controllers;

import be.be_new.dto.request.TestScheduleRequest;
import be.be_new.dto.response.ApiResponse;
import be.be_new.dto.response.TestScheduleResponse;
import be.be_new.services.TestScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class TestScheduleController {

    private final TestScheduleService testScheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<TestScheduleResponse>> saveOrUpdate(@RequestBody TestScheduleRequest dto) {
        TestScheduleResponse result = testScheduleService.saveOrUpdate(dto);
        return ResponseEntity.ok(
                ApiResponse.<TestScheduleResponse>builder()
                        .code(200)
                        .message("Thành công")
                        .data(result)
                        .build()
        );
    }

    @PutMapping("/{id}/increase-registration")
    public ResponseEntity<ApiResponse<Void>> increaseRegistration(@PathVariable Integer id) {
        testScheduleService.increaseRegistrationCount(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .code(200)
                        .message("Tăng số lượng thí sinh thành công")
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TestScheduleResponse>>> getAllSchedules() {
        List<TestScheduleResponse> list = testScheduleService.findAll();
        return ResponseEntity.ok(
                ApiResponse.<List<TestScheduleResponse>>builder()
                        .code(200)
                        .message("Lấy danh sách thành công")
                        .data(list)
                        .build()
        );
    }
}

