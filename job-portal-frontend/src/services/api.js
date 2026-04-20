import axios from "axios";

const BASE_URL = "https://job-portal-o3rj.onrender.com/api";

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Automatically add token to every request
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// Auth APIs
export const registerUser = (data) => api.post("/auth/register", data);

export const loginUser = (data) => api.post("/auth/login", data);

// Job APIs
export const getAllJobs = () => api.get("/jobs/all");

export const searchJobs = (keyword) =>
  api.get(`/jobs/search?keyword=${keyword}`);

export const postJob = (data) => api.post("/jobs/post", data);

export const deleteJob = (id) => api.delete(`/jobs/delete/${id}`);

export const getMyJobs = () => api.get("/jobs/my-jobs");

// Application APIs
export const applyForJob = (jobId, data) =>
  api.post(`/applications/apply/${jobId}`, data);

export const getMyApplications = () => api.get("/applications/my-applications");

export const getApplicationsForJob = (jobId) =>
  api.get(`/applications/job/${jobId}`);

export const updateApplicationStatus = (applicationId, status) =>
  api.put(`/applications/update-status/${applicationId}`, { status });

export default api;
