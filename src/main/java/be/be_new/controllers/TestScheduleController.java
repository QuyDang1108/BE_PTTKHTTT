package be.be_new.controllers;

import be.be_new.dto.request.TestScheduleRequest;
import be.be_new.dto.response.ApiResponse;
import be.be_new.dto.response.TestScheduleResponse;
import be.be_new.services.TestScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
@Tag(name = "Lịch thi", description = "Quản lý lịch thi cho các chứng chỉ")
public class TestScheduleController {

    private final TestScheduleService testScheduleService;

    @PostMapping
    @Operation(
            summary = "Tạo hoặc cập nhật lịch thi",
            description = "Tạo mới hoặc cập nhật thông tin lịch thi cho một chứng chỉ.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TestScheduleRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "id": null,
                                              "date": "2025-08-10",
                                              "time": "08:30:00",
                                              "room": "Phòng 101",
                                              "maxCount": 30,
                                              "nameTest": "Thi chứng chỉ A",
                                              "certificationId": 1
                                            }"""
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Lịch thi đã được lưu thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TestScheduleResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Dữ liệu không hợp lệ hoặc chứng chỉ không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<TestScheduleResponse>> saveOrUpdate(
            @RequestBody TestScheduleRequest dto) {
        TestScheduleResponse result = testScheduleService.saveOrUpdate(dto);
        return ResponseEntity.ok(
                ApiResponse.<TestScheduleResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Tạo hoặc cập nhật lịch thi thành công")
                        .data(result)
                        .build()
        );
    }

    @GetMapping
    @Operation(
            summary = "Lấy danh sách lịch thi",
            description = "Lấy tất cả lịch thi hiện có trong hệ thống.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Danh sách lịch thi",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TestScheduleResponse.class)
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<TestScheduleResponse>>> getAllSchedules() {
        List<TestScheduleResponse> list = testScheduleService.findAll();
        return ResponseEntity.ok(
                ApiResponse.<List<TestScheduleResponse>>builder()
                        .code(HttpStatus.OK.value())
                        .message("Lấy danh sách lịch thi thành công")
                        .data(list)
                        .build()
        );
    }
}
