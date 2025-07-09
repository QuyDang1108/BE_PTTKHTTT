package be.be_new.services;

import be.be_new.dto.request.AuthenticationRequest;
import be.be_new.dto.response.AuthenticationResponse;
import be.be_new.entity.Staff;
import be.be_new.enums.EStaffRole;
import be.be_new.exception.AppException;
import be.be_new.exception.ErrorCode;
import be.be_new.repository.StaffRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final StaffRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @Value("${jwt.signerKey}")
    private String secretKey;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Staff user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .staffId(user.getId())
                .role(user.getRole().toString())
                .build();
    }

    private String generateToken(Staff user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail()) // Email là định danh tốt hơn tên
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("scope", buildScope(user))
                .claim("staffId", user.getId())
                .claim("name", user.getName())
                .build();

        try {
            JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException("Token creation failed", e);
        }
    }

    private String buildScope(Staff user) {
        EStaffRole role = user.getRole();
        return role != null ? "ROLE_" + role.name() : "";
    }
}
