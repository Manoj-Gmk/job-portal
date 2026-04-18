package com.jobportal.job_portal.controller;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.model.Job;
import com.jobportal.job_portal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<Job> postJob(
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(jobService.postJob(request, email));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location) {
        if (keyword != null) {
            return ResponseEntity.ok(
                    jobService.searchByKeyword(keyword));
        } else if (location != null) {
            return ResponseEntity.ok(
                    jobService.searchByLocation(location));
        }
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        jobService.deleteJob(id, email);
        return ResponseEntity.ok("Job deleted successfully");
    }

    @GetMapping("/my-jobs")
    public ResponseEntity<List<Job>> getMyJobs(
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(jobService.getJobsByRecruiter(email));
    }
}