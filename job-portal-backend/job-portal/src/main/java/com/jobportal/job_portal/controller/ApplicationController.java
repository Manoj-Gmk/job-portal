package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.model.JobApplication;
import com.jobportal.job_portal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplication> applyForJob(
            @PathVariable Long jobId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        String email = authentication.getName();
        String coverLetter = body.get("coverLetter");
        return ResponseEntity.ok(
                applicationService.applyForJob(jobId, email, coverLetter));
    }

    @GetMapping("/my-applications")
    public ResponseEntity<List<JobApplication>> getMyApplications(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(
                applicationService.getMyApplications(email));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<JobApplication>> getApplicationsForJob(
            @PathVariable Long jobId,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(
                applicationService.getApplicationsForJob(jobId, email));
    }

    @PutMapping("/update-status/{applicationId}")
    public ResponseEntity<JobApplication> updateStatus(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        String email = authentication.getName();
        String status = body.get("status");
        return ResponseEntity.ok(
                applicationService.updateStatus(
                        applicationId, status, email));
    }
}