package be.be_new.controllers;

import be.be_new.dto.request.RegisFormFullRequest;
import be.be_new.dto.response.RegisFormFullResponse;
import be.be_new.services.RegisFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regis-forms")
public class RegisFormController {

    private final RegisFormService regisFormService;

    @PostMapping
    public ResponseEntity<RegisFormFullResponse> createOrUpdate(@RequestBody RegisFormFullRequest request) {
        return ResponseEntity.ok(regisFormService.createOrUpdate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegisFormFullResponse> getDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(regisFormService.getDetails(id));
    }

    @GetMapping
    public ResponseEntity<List<RegisFormFullResponse>> getAll() {
        return ResponseEntity.ok(regisFormService.getAll());
    }
}

