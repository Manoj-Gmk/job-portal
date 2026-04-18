import { useState, useEffect } from "react";
import {
  getMyJobs,
  postJob,
  deleteJob,
  getApplicationsForJob,
  updateApplicationStatus,
} from "../services/api";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const RecruiterDashboard = () => {
  const [jobs, setJobs] = useState([]);
  const [applications, setApplications] = useState([]);
  const [selectedJob, setSelectedJob] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [message, setMessage] = useState("");
  const [jobForm, setJobForm] = useState({
    title: "",
    description: "",
    company: "",
    location: "",
    salary: "",
    experience: "",
    jobType: "FULL_TIME",
  });

  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user || user.role !== "RECRUITER") {
      navigate("/login");
      return;
    }
    fetchMyJobs();
  }, []);

  const fetchMyJobs = async () => {
    try {
      const response = await getMyJobs();
      setJobs(response.data);
    } catch (err) {
      console.error("Error fetching jobs", err);
    } finally {
      setLoading(false);
    }
  };

  const handleFormChange = (e) => {
    setJobForm({ ...jobForm, [e.target.name]: e.target.value });
  };

  const handlePostJob = async (e) => {
    e.preventDefault();
    try {
      await postJob(jobForm);
      setMessage("Job posted successfully!");
      setShowForm(false);
      setJobForm({
        title: "",
        description: "",
        company: "",
        location: "",
        salary: "",
        experience: "",
        jobType: "FULL_TIME",
      });
      fetchMyJobs();
    } catch (err) {
      setMessage("Error posting job. Please try again.");
    }
  };

  const handleDeleteJob = async (id) => {
    if (window.confirm("Are you sure you want to delete this job?")) {
      try {
        await deleteJob(id);
        setMessage("Job deleted successfully!");
        fetchMyJobs();
      } catch (err) {
        setMessage("Error deleting job.");
      }
    }
  };

  const handleViewApplications = async (job) => {
    setSelectedJob(job);
    try {
      const response = await getApplicationsForJob(job.id);
      setApplications(response.data);
    } catch (err) {
      console.error("Error fetching applications", err);
    }
  };

  const handleUpdateStatus = async (applicationId, status) => {
    try {
      await updateApplicationStatus(applicationId, status);
      setMessage(`Application ${status} successfully!`);
      handleViewApplications(selectedJob);
    } catch (err) {
      setMessage("Error updating status.");
    }
  };

  const getStatusBadge = (status) => {
    switch (status) {
      case "APPLIED":
        return "bg-primary";
      case "REVIEWED":
        return "bg-warning";
      case "ACCEPTED":
        return "bg-success";
      case "REJECTED":
        return "bg-danger";
      default:
        return "bg-secondary";
    }
  };

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="text-primary">🏢 Recruiter Dashboard</h2>
        <button
          className="btn btn-primary"
          onClick={() => setShowForm(!showForm)}
        >
          {showForm ? "Cancel" : "+ Post New Job"}
        </button>
      </div>

      <p className="text-muted">Welcome, {user?.fullName}!</p>

      {message && (
        <div className="alert alert-info alert-dismissible">
          {message}
          <button
            type="button"
            className="btn-close"
            onClick={() => setMessage("")}
          ></button>
        </div>
      )}

      {/* Post Job Form */}
      {showForm && (
        <div className="card shadow mb-4">
          <div className="card-body">
            <h5 className="mb-3">Post a New Job</h5>
            <form onSubmit={handlePostJob}>
              <div className="row">
                <div className="col-md-6 mb-3">
                  <input
                    type="text"
                    name="title"
                    className="form-control"
                    placeholder="Job Title"
                    value={jobForm.title}
                    onChange={handleFormChange}
                    required
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <input
                    type="text"
                    name="company"
                    className="form-control"
                    placeholder="Company Name"
                    value={jobForm.company}
                    onChange={handleFormChange}
                    required
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <input
                    type="text"
                    name="location"
                    className="form-control"
                    placeholder="Location"
                    value={jobForm.location}
                    onChange={handleFormChange}
                    required
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <input
                    type="text"
                    name="salary"
                    className="form-control"
                    placeholder="Salary (e.g. 4-6 LPA)"
                    value={jobForm.salary}
                    onChange={handleFormChange}
                    required
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <input
                    type="text"
                    name="experience"
                    className="form-control"
                    placeholder="Experience (e.g. 0-2 Years)"
                    value={jobForm.experience}
                    onChange={handleFormChange}
                    required
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <select
                    name="jobType"
                    className="form-select"
                    value={jobForm.jobType}
                    onChange={handleFormChange}
                  >
                    <option value="FULL_TIME">Full Time</option>
                    <option value="PART_TIME">Part Time</option>
                    <option value="INTERNSHIP">Internship</option>
                    <option value="REMOTE">Remote</option>
                  </select>
                </div>
                <div className="col-12 mb-3">
                  <textarea
                    name="description"
                    className="form-control"
                    placeholder="Job Description"
                    rows="4"
                    value={jobForm.description}
                    onChange={handleFormChange}
                    required
                  ></textarea>
                </div>
              </div>
              <button type="submit" className="btn btn-success w-100">
                Post Job
              </button>
            </form>
          </div>
        </div>
      )}

      {/* My Jobs List */}
      {loading ? (
        <div className="text-center mt-5">
          <div className="spinner-border text-primary"></div>
        </div>
      ) : jobs.length === 0 ? (
        <div className="text-center mt-5">
          <h5>You haven't posted any jobs yet</h5>
        </div>
      ) : (
        <div className="row">
          {jobs.map((job) => (
            <div className="col-md-6 mb-4" key={job.id}>
              <div className="card shadow-sm h-100">
                <div className="card-body">
                  <h5 className="card-title text-primary">{job.title}</h5>
                  <h6 className="text-muted">🏢 {job.company}</h6>
                  <p className="mb-1">📍 {job.location}</p>
                  <p className="mb-1">💰 {job.salary}</p>
                  <p className="mb-1">🎯 {job.experience}</p>
                  <span className="badge bg-success mb-2">{job.jobType}</span>
                </div>
                <div className="card-footer d-flex gap-2">
                  <button
                    className="btn btn-outline-primary btn-sm flex-fill"
                    onClick={() => handleViewApplications(job)}
                  >
                    View Applications
                  </button>
                  <button
                    className="btn btn-outline-danger btn-sm flex-fill"
                    onClick={() => handleDeleteJob(job.id)}
                  >
                    Delete Job
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Applications Modal */}
      {selectedJob && (
        <div className="mt-4">
          <h4 className="text-primary">
            Applications for: {selectedJob.title}
          </h4>
          <button
            className="btn btn-outline-secondary btn-sm mb-3"
            onClick={() => setSelectedJob(null)}
          >
            Close
          </button>

          {applications.length === 0 ? (
            <p>No applications yet for this job.</p>
          ) : (
            <div className="row">
              {applications.map((app) => (
                <div className="col-md-6 mb-3" key={app.id}>
                  <div className="card shadow-sm">
                    <div className="card-body">
                      <h6 className="text-primary">
                        👤 {app.candidate.fullName}
                      </h6>
                      <p className="mb-1 small">📧 {app.candidate.email}</p>
                      <p
                        className="mb-2 small 
                                                text-muted"
                      >
                        {app.coverLetter}
                      </p>
                      <span
                        className={`badge 
                                                ${getStatusBadge(app.status)}`}
                      >
                        {app.status}
                      </span>
                    </div>
                    <div className="card-footer d-flex gap-2">
                      <button
                        className="btn btn-success btn-sm flex-fill"
                        onClick={() => handleUpdateStatus(app.id, "ACCEPTED")}
                      >
                        Accept
                      </button>
                      <button
                        className="btn btn-danger btn-sm flex-fill"
                        onClick={() => handleUpdateStatus(app.id, "REJECTED")}
                      >
                        Reject
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default RecruiterDashboard;
