package be.be_new.controllers;

import be.be_new.dto.response.ApiResponse;
import be.be_new.dto.response.ExaminationPermitResponse;
import be.be_new.services.ExaminationPermitService;
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
@RequestMapping("/permits")
@RequiredArgsConstructor
@Tag(name = "Phiếu dự thi", description = "Tạo và xem thông tin phiếu dự thi từ phiếu đăng ký")
public class ExaminationPermitController {

    private final ExaminationPermitService examinationPermitService;

    @PostMapping("/{id}")
    @Operation(
            summary = "Tạo phiếu dự thi",
            description = "Tạo phiếu dự thi từ phiếu đăng ký nếu thanh toán đã hoàn tất.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Tạo thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExaminationPermitResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Phiếu đăng ký không tồn tại hoặc chưa thanh toán",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n  \"code\": 1007,\n  \"message\": \"payment not completed\"\n}"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<List<ExaminationPermitResponse>>> createByRegisForm(
            @PathVariable Integer id) {
        List<ExaminationPermitResponse> permits = examinationPermitService.createFromRegisForm(id);
        return ResponseEntity.ok(ApiResponse.<List<ExaminationPermitResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Tạo phiếu dự thi thành công từ phiếu đăng ký")
                .data(permits)
                .build());
    }

    @GetMapping
    @Operation(
            summary = "Lấy danh sách tất cả phiếu dự thi",
            description = "Trả về danh sách đầy đủ các phiếu dự thi.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Danh sách phiếu dự thi",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ExaminationPermitResponse.class)
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<ExaminationPermitResponse>>> getAll() {
        List<ExaminationPermitResponse> permits = examinationPermitService.getAllPermits();
        return ResponseEntity.ok(ApiResponse.<List<ExaminationPermitResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phiếu dự thi thành công")
                .data(permits)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Xem chi tiết phiếu dự thi",
            description = "Lấy thông tin chi tiết của một phiếu dự thi theo ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Chi tiết phiếu dự thi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExaminationPermitResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Không tìm thấy phiếu dự thi",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n  \"code\": 400,\n  \"message\": \"not found data\"\n}"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<ExaminationPermitResponse>> getDetails(@PathVariable Integer id) {
        ExaminationPermitResponse permit = examinationPermitService.getPermitDetails(id);
        return ResponseEntity.ok(ApiResponse.<ExaminationPermitResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết phiếu dự thi thành công")
                .data(permit)
                .build());
    }
}
