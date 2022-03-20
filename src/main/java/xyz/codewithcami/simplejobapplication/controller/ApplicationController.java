package xyz.codewithcami.simplejobapplication.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.codewithcami.simplejobapplication.models.Application;
import xyz.codewithcami.simplejobapplication.repository.ApplicationRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;

    public ApplicationController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @GetMapping("/all")
    public List<Application> getApplications() {
        return applicationRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplication(@PathVariable Long id) {
        Application application = applicationRepository.findById(id).orElse(null);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(application);

    }

    @PostMapping("/submit")
    public ResponseEntity<Application> submitApplication(@Valid @RequestBody Application application) throws URISyntaxException {
        Application savedModel = applicationRepository.save(application);
        return ResponseEntity.created(new URI("/application/" + savedModel.getId())).body(savedModel);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, @Valid @RequestParam String status) {
        Application application = applicationRepository.findById(id).orElse(null);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }
        application.setStatus(status);
        Application savedModel = applicationRepository.save(application);

        return ResponseEntity.ok(savedModel);
    }
}
