package com.jobportal.job_portal.service;

import com.jobportal.job_portal.model.Job;
import com.jobportal.job_portal.model.JobApplication;
import com.jobportal.job_portal.model.User;
import com.jobportal.job_portal.repository.JobApplicationRepository;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobApplication applyForJob(Long jobId,
                                      String candidateEmail, String coverLetter) {

        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (applicationRepository.existsByCandidateAndJob(
                candidate, job)) {
            throw new RuntimeException(
                    "You have already applied for this job");
        }

        JobApplication application = new JobApplication();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setCoverLetter(coverLetter);

        return applicationRepository.save(application);
    }

    public List<JobApplication> getMyApplications(
            String candidateEmail) {
        User candidate = userRepository.findByEmail(candidateEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
        return applicationRepository.findByCandidate(candidate);
    }

    public List<JobApplication> getApplicationsForJob(
            Long jobId, String recruiterEmail) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (!job.getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException(
                    "You are not authorized to view these applications");
        }

        return applicationRepository.findByJob(job);
    }

    public JobApplication updateStatus(Long applicationId,
                                       String status, String recruiterEmail) {

        JobApplication application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        if (!application.getJob().getRecruiter()
                .getEmail().equals(recruiterEmail)) {
            throw new RuntimeException(
                    "You are not authorized to update this application");
        }

        application.setStatus(
                JobApplication.Status.valueOf(status.toUpperCase()));

        return applicationRepository.save(application);
    }
}