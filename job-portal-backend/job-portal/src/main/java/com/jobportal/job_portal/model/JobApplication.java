package com.jobportal.job_portal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.APPLIED;

    @Column(nullable = false)
    private LocalDateTime appliedAt = LocalDateTime.now();

    @Column(length = 1000)
    private String coverLetter;

    public enum Status {
        APPLIED,
        REVIEWED,
        ACCEPTED,
        REJECTED
    }
}
