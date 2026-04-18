package com.jobportal.job_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String salary;

    @Column(nullable = false)
    private String experience;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Column(nullable = false)
    private LocalDateTime postedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    public enum JobType {
        FULL_TIME,
        PART_TIME,
        INTERNSHIP,
        REMOTE
    }
}
