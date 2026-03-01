import { useState, useContext, useEffect } from "react";
import API from "../api/api";
import { AuthContext } from "../context/AuthContext";
import { useNavigate, Link } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    localStorage.removeItem("token");
  }, []);

  const handleLogin = async (e) => {
    e.preventDefault(); // Prevent page refresh
    try {
      localStorage.removeItem("token");
      const res = await API.post("/auth/login", { username, password });
      login(res.data.token);
      navigate("/dashboard");
    } catch (err) {
      alert("Login failed: " + (err.response?.data?.message || err.message));
      logout();
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="card" style={{ width: "100%", maxWidth: "400px" }}>
        <h2>Login</h2>
        <input 
          type="text" 
          placeholder="Username" 
          onChange={(e) => setUsername(e.target.value)} 
        />
        <input 
          type="password" 
          placeholder="Password" 
          onChange={(e) => setPassword(e.target.value)} 
        />
        <button onClick={handleLogin} style={{ width: "100%" }}>
          Login
        </button>
        <p style={{ textAlign: "center", marginTop: "1rem", color: "#64748b" }}>
          Don't have an account? <Link to="/register" style={{ color: "#3b82f6" }}>Register</Link>
        </p>
      </div>
    </div>
  );
}

export default Login;