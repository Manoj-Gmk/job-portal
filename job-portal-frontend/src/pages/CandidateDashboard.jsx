import { useState, useEffect } from "react";
import { getMyApplications } from "../services/api";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const CandidateDashboard = () => {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!user || user.role !== "CANDIDATE") {
      navigate("/login");
      return;
    }
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    try {
      const response = await getMyApplications();
      setApplications(response.data);
    } catch (err) {
      console.error("Error fetching applications", err);
    } finally {
      setLoading(false);
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
      <h2 className="mb-4 text-primary">👤 My Applications</h2>
      <p className="text-muted">Welcome, {user?.fullName}!</p>

      {loading ? (
        <div className="text-center mt-5">
          <div className="spinner-border text-primary"></div>
        </div>
      ) : applications.length === 0 ? (
        <div className="text-center mt-5">
          <h5>You haven't applied for any jobs yet</h5>
          <button
            className="btn btn-primary mt-3"
            onClick={() => navigate("/jobs")}
          >
            Browse Jobs
          </button>
        </div>
      ) : (
        <div className="row">
          {applications.map((app) => (
            <div className="col-md-6 mb-4" key={app.id}>
              <div className="card shadow-sm h-100">
                <div className="card-body">
                  <h5 className="card-title text-primary">{app.job.title}</h5>
                  <h6 className="text-muted">🏢 {app.job.company}</h6>
                  <p className="mb-1">📍 {app.job.location}</p>
                  <p className="mb-1">💰 {app.job.salary}</p>
                  <p className="mb-2">🎯 {app.job.experience}</p>
                  <p className="small text-muted">
                    Applied: {new Date(app.appliedAt).toLocaleDateString()}
                  </p>
                </div>
                <div className="card-footer d-flex justify-content-between align-items-center">
                  <span>Application Status:</span>
                  <span className={`badge ${getStatusBadge(app.status)}`}>
                    {app.status}
                  </span>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default CandidateDashboard;
