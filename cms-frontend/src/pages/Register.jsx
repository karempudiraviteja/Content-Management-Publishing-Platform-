import { useState } from "react";
import API from "../api/api";
import { useNavigate, Link } from "react-router-dom";

function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("ROLE_AUTHOR");
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await API.post("/users/register", { username, password, role });
      alert("Registered successfully!");
      navigate("/");
    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="card" style={{ width: "100%", maxWidth: "400px" }}>
        <h2>Register</h2>
        <input 
          placeholder="Username" 
          onChange={(e) => setUsername(e.target.value)} 
        />
        <input 
          type="password" 
          placeholder="Password" 
          onChange={(e) => setPassword(e.target.value)} 
        />
        <select onChange={(e) => setRole(e.target.value)}>
          <option value="ROLE_AUTHOR">Author</option>
          <option value="ROLE_REVIEWER">Reviewer</option>
          <option value="ROLE_PUBLISHER">Publisher</option>
          <option value="ROLE_SUPER_ADMIN">Super Admin</option>
        </select>
        <button onClick={handleRegister} style={{ width: "100%" }}>
          Register
        </button>
        <p style={{ textAlign: "center", marginTop: "1rem", color: "#64748b" }}>
          Already have an account? <Link to="/login" style={{ color: "#3b82f6" }}>Login</Link>
        </p>
      </div>
    </div>
  );
}

export default Register;