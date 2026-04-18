package com.jobportal.job_portal.service;

import com.jobportal.job_portal.dto.JobRequest;
import com.jobportal.job_portal.model.Job;
import com.jobportal.job_portal.model.User;
import com.jobportal.job_portal.repository.JobRepository;
import com.jobportal.job_portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public Job postJob(JobRequest request, String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setExperience(request.getExperience());
        job.setJobType(request.getJobType());
        job.setRecruiter(recruiter);

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    //public List<Job> searchByKeyword(String keyword) {
       // return jobRepository.findByTitleContaining(keyword);
    //}
    public List<Job> searchByKeyword(String keyword) {
        return jobRepository.searchByKeyword(keyword);
    }

    public List<Job> searchByLocation(String location) {
        return jobRepository.findByLocation(location);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void deleteJob(Long id, String recruiterEmail) {
        Job job = getJobById(id);
        if (!job.getRecruiter().getEmail().equals(recruiterEmail)) {
            throw new RuntimeException(
                    "You are not authorized to delete this job");
        }
        jobRepository.delete(job);
    }

    public List<Job> getJobsByRecruiter(String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return jobRepository.findByRecruiter(recruiter);
    }
}