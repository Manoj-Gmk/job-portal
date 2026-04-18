import React from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Home = () => {
  const navigate = useNavigate();
  const { user } = useAuth();

  return (
    <div>
      {/* Hero Section */}
      <div className="bg-primary text-white py-5">
        <div className="container text-center">
          <h1 className="display-4 fw-bold">💼 Find Your Dream Job</h1>
          <p className="lead mt-3">
            Connect with top companies and opportunities across India
          </p>
          <div className="mt-4 d-flex gap-3 justify-content-center">
            <button
              className="btn btn-light btn-lg"
              onClick={() => navigate("/jobs")}
            >
              Browse Jobs
            </button>
            {!user && (
              <button
                className="btn btn-outline-light btn-lg"
                onClick={() => navigate("/register")}
              >
                Get Started
              </button>
            )}
          </div>
        </div>
      </div>

      {/* Stats Section */}
      <div className="container mt-5">
        <div className="row text-center">
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4">
              <h2 className="text-primary fw-bold">500+</h2>
              <p className="text-muted">Jobs Available</p>
            </div>
          </div>
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4">
              <h2 className="text-primary fw-bold">200+</h2>
              <p className="text-muted">Companies Hiring</p>
            </div>
          </div>
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4">
              <h2 className="text-primary fw-bold">1000+</h2>
              <p className="text-muted">Candidates Placed</p>
            </div>
          </div>
        </div>
      </div>

      {/* Features Section */}
      <div className="container mt-4 mb-5">
        <h3 className="text-center mb-4">Why Choose Us?</h3>
        <div className="row">
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4 text-center">
              <h1>🔍</h1>
              <h5 className="mt-2">Smart Job Search</h5>
              <p className="text-muted">
                Search jobs by title, location and experience level
              </p>
            </div>
          </div>
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4 text-center">
              <h1>⚡</h1>
              <h5 className="mt-2">Quick Apply</h5>
              <p className="text-muted">
                Apply for jobs with one click and track your applications
              </p>
            </div>
          </div>
          <div className="col-md-4 mb-4">
            <div className="card shadow-sm p-4 text-center">
              <h1>🏢</h1>
              <h5 className="mt-2">Top Companies</h5>
              <p className="text-muted">
                Connect with leading companies across all industries
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
