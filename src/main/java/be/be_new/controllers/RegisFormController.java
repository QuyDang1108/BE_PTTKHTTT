package be.be_new.controllers;

import be.be_new.dto.request.RegisFormFullRequest;
import be.be_new.dto.response.ApiResponse;
import be.be_new.dto.response.RegisFormFullResponse;
import be.be_new.services.RegisFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regis")
@Tag(name = "Phiếu đăng ký", description = "Quản lý phiếu đăng ký thi")
public class RegisFormController {

    private final RegisFormService regisFormService;

    @PostMapping
    @Operation(
            summary = "Tạo hoặc cập nhật phiếu đăng ký",
            description = "Tạo mới hoặc cập nhật phiếu đăng ký, bao gồm thông tin người đăng ký và danh sách thí sinh.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisFormFullRequest.class),
                            examples = @ExampleObject(
                                    name = "Request Example",
                                    value = """
                                            {
                                              "formId": null,
                                              "date": "2025-08-01",
                                              "status": "Đang xử lý",
                                              "registrantType": "INDIVIDUAL",
                                              "registrant": {
                                                "name": "Nguyễn Văn A",
                                                "phone": "0912345678",
                                                "email": "a@gmail.com",
                                                "address": "Hà Nội",
                                                "dob": "1990-01-01",
                                                "noc": "123",
                                                "aoc": "456",
                                                "poc": "789"
                                              },
                                              "candidates": [
                                                {
                                                  "name": "Thí sinh 1",
                                                  "dob": "2000-01-01",
                                                  "email": "thi1@gmail.com",
                                                  "phone": "091234567",
                                                  "address": "Địa chỉ 1"
                                                }
                                              ],
                                              "staffId": 1,
                                              "testScheduleId": 2
                                            }"""
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Tạo hoặc cập nhật phiếu đăng ký thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisFormFullResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<RegisFormFullResponse>> createOrUpdate(
            @Valid @RequestBody RegisFormFullRequest request) {
        RegisFormFullResponse response = regisFormService.createOrUpdate(request);
        return ResponseEntity.ok(ApiResponse.<RegisFormFullResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Tạo hoặc cập nhật phiếu đăng ký thành công")
                .data(response)
                .build());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lấy chi tiết phiếu đăng ký",
            description = "Lấy thông tin chi tiết của phiếu đăng ký theo ID.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Thông tin chi tiết phiếu đăng ký",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisFormFullResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Không tìm thấy phiếu đăng ký",
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
    public ResponseEntity<ApiResponse<RegisFormFullResponse>> getDetails(@PathVariable Integer id) {
        RegisFormFullResponse response = regisFormService.getDetails(id);
        return ResponseEntity.ok(ApiResponse.<RegisFormFullResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy chi tiết phiếu đăng ký thành công")
                .data(response)
                .build());
    }

    @GetMapping
    @Operation(
            summary = "Lấy tất cả phiếu đăng ký",
            description = "Trả về danh sách tất cả phiếu đăng ký.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Danh sách phiếu đăng ký",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegisFormFullResponse.class)
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<RegisFormFullResponse>>> getAll() {
        List<RegisFormFullResponse> responseList = regisFormService.getAll();
        return ResponseEntity.ok(ApiResponse.<List<RegisFormFullResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phiếu đăng ký thành công")
                .data(responseList)
                .build());
    }

    @PutMapping("/{formId}/status")
    @Operation(
            summary = "Cập nhật trạng thái phiếu đăng ký nếu đã thanh toán",
            description = "Chỉ cho phép cập nhật trạng thái nếu phiếu đăng ký đã có phiếu thanh toán.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Cập nhật trạng thái thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                  "code": 200,
                                                  "message": "Status updated successfully"
                                                }"""
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Phiếu đăng ký chưa được thanh toán hoặc không tồn tại",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                {
                                                  "code": 400,
                                                  "message": "Registration form has not been paid yet."
                                                }"""
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ApiResponse<Void>> updateStatusIfPaid(
            @PathVariable Integer formId,
            @RequestParam String status
    ) {
        regisFormService.updatePaymentStatusForForm(formId, status);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thành công!")
                .build());
    }

}
