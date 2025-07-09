package be.be_new.services;

import be.be_new.dto.response.CandidateResponse;
import be.be_new.entity.Candidate;
import be.be_new.entity.RegisForm;
import be.be_new.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public List<CandidateResponse> getCandidatesNotHavePermit() {
        List<Candidate> candidates = candidateRepository.findAll();
        List<CandidateResponse> candidateResponses = new ArrayList<>();
        for (Candidate candidate : candidates) {
            if (candidate.getExaminationPermit() == null && Objects.equals(candidate.getRegisForm().getStatus(), "Đã thanh toán")) {
                candidateResponses.add(toResponse(candidate));
            }
        }

        return candidateResponses;
    }

    CandidateResponse toResponse(Candidate candidate) {
        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setId(candidate.getId());
        candidateResponse.setName(candidate.getName());
        candidateResponse.setPhone(candidate.getPhone());
        candidateResponse.setEmail(candidate.getEmail());
        candidateResponse.setAddress(candidate.getAddress());
        candidateResponse.setDob(candidate.getDob());
        candidateResponse.setRegisId(candidate.getRegisForm().getId());
        if (candidate.getExaminationPermit() != null) {
            candidateResponse.setPermitId(candidate.getExaminationPermit().getId());
        }
        return candidateResponse;
    }
}
