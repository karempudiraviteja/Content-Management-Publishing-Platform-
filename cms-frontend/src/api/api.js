import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:9632",
});

// Add this interceptor to attach the JWT to every request
API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
 // ONLY attach token if it exists AND we aren't hitting the login/register routes
  if (token && !config.url.includes("/auth/")) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default API;