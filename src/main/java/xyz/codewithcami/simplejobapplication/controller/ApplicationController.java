package xyz.codewithcami.simplejobapplication.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.codewithcami.simplejobapplication.models.Application;
import xyz.codewithcami.simplejobapplication.repository.ApplicationRepository;
import xyz.codewithcami.simplejobapplication.services.FilesStorageService;
import xyz.codewithcami.simplejobapplication.utils.FileUploadHelper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    private final FilesStorageService filesStorageService;

    public ApplicationController(ApplicationRepository applicationRepository, FilesStorageService filesStorageService) {
        this.applicationRepository = applicationRepository;
        this.filesStorageService = filesStorageService;
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
    public ResponseEntity<Application> submitApplication(@Valid Application application,
                                                         @RequestPart("cvAttachment") MultipartFile file)
            throws URISyntaxException {

        // upload file
        String fileName = uploadFile(file);

        application.setCv(fileName);
        Application savedModel = applicationRepository.save(application);
        return ResponseEntity.created(new URI("/application/" + savedModel.getId())).body(savedModel);
    }

    private String uploadFile(MultipartFile file) {
        String fileName = null;
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            // replace spaces with underscores
            originalFilename = Objects.requireNonNull(originalFilename).replaceAll("\\s+", "_");
            fileName = System.currentTimeMillis() + StringUtils.cleanPath(Objects.requireNonNull(originalFilename));


            String uploadDir = "/applications";

            try {
                FileUploadHelper.saveFile(uploadDir, fileName, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
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


    @GetMapping("/{id}/download/cv")
    public Object download(@PathVariable("id") long id, RedirectAttributes ra, HttpServletRequest request) throws IOException {
        Application application = applicationRepository.findById(id).orElse(null);
        if (application == null) {
            ra.addFlashAttribute("error", "Purchase is not found!");
            return "redirect:" + request.getHeader("Referer");
        }


        Resource resource = filesStorageService.load("applications/" + application.getCv());
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + application.getCv() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
