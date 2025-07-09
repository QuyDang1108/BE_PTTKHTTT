package be.be_new.controllers;

import be.be_new.dto.request.PaymentRequest;
import be.be_new.dto.response.ApiResponse;
import be.be_new.dto.response.PaymentReceiptResponse;
import be.be_new.services.PaymentReceiptService;
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
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Phiếu thanh toán", description = "Quản lý phiếu thanh toán từ phiếu đăng ký")
public class PaymentReceiptController {

    private final PaymentReceiptService paymentReceiptService;

    @PostMapping
    @Operation(
            summary = "Tạo phiếu thanh toán",
            description = "Tạo mới phiếu thanh toán từ phiếu đăng ký đã có.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PaymentRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "money": 500000,
                                              "dop": "2025-08-01",
                                              "status": "Đã thanh toán",
                                              "paymentType": "Chuyển khoản",
                                              "formId": 1,
                                              "staffId": 2
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Tạo phiếu thanh toán thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentReceiptResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Phiếu đăng ký hoặc nhân viên không tồn tại"
                    )
            }
    )
    public ResponseEntity<ApiResponse<PaymentReceiptResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentReceiptResponse response = paymentReceiptService.createFromRegisForm(request);
        return ResponseEntity.ok(ApiResponse.<PaymentReceiptResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Tạo phiếu thanh toán thành công")
                .data(response)
                .build());
    }

    @GetMapping("/form/{formId}")
    @Operation(
            summary = "Lấy phiếu thanh toán theo phiếu đăng ký",
            description = "Truy vấn phiếu thanh toán dựa trên ID của phiếu đăng ký",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Thông tin phiếu thanh toán",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaymentReceiptResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Không tìm thấy dữ liệu"
                    )
            }
    )
    public ResponseEntity<ApiResponse<PaymentReceiptResponse>> getByFormId(@PathVariable Integer formId) {
        PaymentReceiptResponse response = paymentReceiptService.getByRegisFormId(formId);
        return ResponseEntity.ok(ApiResponse.<PaymentReceiptResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy phiếu thanh toán thành công")
                .data(response)
                .build());
    }

    @GetMapping
    @Operation(
            summary = "Lấy tất cả phiếu thanh toán",
            description = "Trả về danh sách tất cả phiếu thanh toán",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Danh sách phiếu thanh toán",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentReceiptResponse.class)
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<PaymentReceiptResponse>>> getAllReceipts() {
        List<PaymentReceiptResponse> list = paymentReceiptService.getAll();
        return ResponseEntity.ok(ApiResponse.<List<PaymentReceiptResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phiếu thanh toán thành công")
                .data(list)
                .build());
    }
}