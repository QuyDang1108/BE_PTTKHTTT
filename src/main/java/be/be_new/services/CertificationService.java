package be.be_new.services;

import be.be_new.dto.response.CertificationResponse;
import be.be_new.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;

    public List<CertificationResponse> getAll() {
        return certificationRepository.findAll().stream()
                .map(cert -> {
                    CertificationResponse dto = new CertificationResponse();
                    dto.setId(cert.getId());
                    dto.setName(cert.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
