package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.model.JobApplication;
import com.jobportal.job_portal.model.User;
import com.jobportal.job_portal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobApplicationRepository
        extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByCandidate(User candidate);
    List<JobApplication> findByJob(Job job);
    Boolean existsByCandidateAndJob(User candidate, Job job);
}
