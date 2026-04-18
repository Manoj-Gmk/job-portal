//package com.jobportal.job_portal.repository;
//
//import com.jobportal.job_portal.model.Job;
//import com.jobportal.job_portal.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//
//@Repository
//public interface JobRepository extends JpaRepository<Job, Long> {
//
//    List<Job> findByLocation(String location);
//    List<Job> findByTitleContaining(String keyword);
//    List<Job> findByRecruiter(User recruiter);
//

package com.jobportal.job_portal.repository;

import com.jobportal.job_portal.model.Job;
import com.jobportal.job_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.experience) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Job> searchByKeyword(@Param("keyword") String keyword);

    List<Job> findByLocation(String location);
    List<Job> findByRecruiter(User recruiter);
}
