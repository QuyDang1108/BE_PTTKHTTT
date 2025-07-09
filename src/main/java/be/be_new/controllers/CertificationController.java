package be.be_new.controllers;

import be.be_new.dto.response.CertificationResponse;
import be.be_new.services.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certifications")
@RequiredArgsConstructor
@Tag(name = "Certification", description = "Quản lý chứng chỉ")
public class CertificationController {

    private final CertificationService certificationService;

    @GetMapping
    @Operation(
            summary = "Lấy danh sách tất cả chứng chỉ",
            description = "Trả về danh sách tất cả các chứng chỉ hiện có.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Thành công",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CertificationResponse.class)))
            }
    )
    public ResponseEntity<List<CertificationResponse>> getAll() {
        return ResponseEntity.ok(certificationService.getAll());
    }
}