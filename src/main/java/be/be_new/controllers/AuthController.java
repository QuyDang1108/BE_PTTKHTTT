package be.be_new.controllers;

import be.be_new.dto.request.AuthenticationRequest;
import be.be_new.dto.response.AuthenticationResponse;
import be.be_new.dto.response.ApiResponse;
import be.be_new.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logins")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Đăng nhập hệ thống cho nhân viên")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập hệ thống với email và mật khẩu.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthenticationRequest.class),
                            examples = @ExampleObject(
                                    value = "{\n  \"email\": \"admin@example.com\",\n  \"password\": \"yourPassword\"\n}"
                            )
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Đăng nhập thành công",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "400",
                            description = "Email không tồn tại hoặc sai mật khẩu",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = @ExampleObject(
                                            value = "{\n  \"code\": 1005,\n  \"message\": \"Unauthenticated\"\n}"
                                    )
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Lỗi hệ thống hoặc không xác thực được",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}
