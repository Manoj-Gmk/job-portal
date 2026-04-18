import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getAllJobs, searchJobs, applyForJob } from "../services/api";
import { useAuth } from "../context/AuthContext";

const Jobs = () => {
  const [jobs, setJobs] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [loading, setLoading] = useState(true);
  const [applying, setApplying] = useState(null);
  const [message, setMessage] = useState("");
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const response = await getAllJobs();
      setJobs(response.data);
    } catch (err) {
      console.error("Error fetching jobs", err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (keyword.trim() === "") {
        fetchJobs();
      } else {
        const response = await searchJobs(keyword);
        setJobs(response.data);
      }
    } catch (err) {
      console.error("Error searching jobs", err);
    } finally {
      setLoading(false);
    }
  };

  const handleApply = async (jobId) => {
    if (!user) {
      navigate("/login");
      return;
    }
    if (user.role !== "CANDIDATE") {
      setMessage("Only candidates can apply for jobs.");
      return;
    }
    setApplying(jobId);
    try {
      await applyForJob(jobId, {
        coverLetter: "I am interested in this position.",
      });
      setMessage("Successfully applied for the job!");
    } catch (err) {
      setMessage("Already applied or error occurred.");
    } finally {
      setApplying(null);
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="mb-4 text-primary">Browse Jobs</h2>

      {/* Search Bar */}
      <form onSubmit={handleSearch} className="mb-4">
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Search jobs by title..."
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
          <button className="btn btn-primary" type="submit">
            Search
          </button>
          <button
            className="btn btn-outline-secondary"
            type="button"
            onClick={() => {
              setKeyword("");
              fetchJobs();
            }}
          >
            Clear
          </button>
        </div>
      </form>

      {/* Message */}
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

      {/* Jobs List */}
      {loading ? (
        <div className="text-center mt-5">
          <div className="spinner-border text-primary"></div>
        </div>
      ) : jobs.length === 0 ? (
        <div className="text-center mt-5">
          <h5>No jobs found</h5>
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
                  <p className="card-text text-muted small">
                    {job.description.substring(0, 100)}...
                  </p>
                  <p className="small text-muted">
                    Posted by: {job.recruiter.fullName}
                  </p>
                </div>
                <div className="card-footer">
                  {user && user.role === "CANDIDATE" && (
                    <button
                      className="btn btn-primary w-100"
                      onClick={() => handleApply(job.id)}
                      disabled={applying === job.id}
                    >
                      {applying === job.id ? "Applying..." : "Apply Now"}
                    </button>
                  )}
                  {!user && (
                    <button
                      className="btn btn-outline-primary w-100"
                      onClick={() => navigate("/login")}
                    >
                      Login to Apply
                    </button>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Jobs;
