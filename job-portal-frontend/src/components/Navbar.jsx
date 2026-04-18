import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  const handleDashboard = () => {
    if (user.role === "CANDIDATE") {
      navigate("/candidate-dashboard");
    } else if (user.role === "RECRUITER") {
      navigate("/recruiter-dashboard");
    }
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container">
        <span className="navbar-brand fw-bold">💼 Job Portal</span>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav ms-auto align-items-center gap-3">
            <li className="nav-item">
              <Link className="nav-link" to="/">
                Home
              </Link>
            </li>

            <li className="nav-item">
              <Link className="nav-link" to="/jobs">
                Browse Jobs
              </Link>
            </li>

            {!user && (
              <>
                <li className="nav-item">
                  <Link className="nav-link" to="/login">
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to="/register">
                    Register
                  </Link>
                </li>
              </>
            )}

            {user && (
              <>
                <li className="nav-item">
                  <span className="navbar-text text-white me-3">
                    Hello, {user?.fullName?.split(" ")[0]}!
                  </span>
                </li>

                <li className="nav-item">
                  <button
                    className="btn btn-outline-light me-2"
                    onClick={handleDashboard}
                  >
                    {user.role === "CANDIDATE"
                      ? "👤 My Applications"
                      : "🏢 My Jobs"}
                  </button>
                </li>

                <li className="nav-item">
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={handleLogout}
                  >
                    Logout
                  </button>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
