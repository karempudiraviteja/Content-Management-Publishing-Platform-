import { Link } from "react-router-dom";
import "../components/layout.css";


function Home() {
  return (
    <div className="page-content">
      {/* Hero Section - Clean & Focused */}
      <section style={{ textAlign: "center", padding: "60px 20px", marginBottom: "40px" }}>
        <h1 style={{ fontSize: "2.5rem", color: "#1e293b", marginBottom: "16px" }}>
          CMS Publishing Platform
        </h1>
        <p style={{ fontSize: "1.1rem", color: "#64748b", maxWidth: "600px", margin: "0 auto 30px" }}>
          A streamlined workflow for content creation, peer review, and global publishing. 
          Manage your editorial pipeline with role-based precision.
        </p>
        <div style={{ display: "flex", gap: "15px", justifyContent: "center" }}>
          <Link to="/login" className="btn-primary" style={{ textDecoration: "none" }}>
            Login to Dashboard
          </Link>
          <Link to="/register" className="btn-primary" style={{ textDecoration: "none", background: "#64748b" }}>
            Create Account
          </Link>
        </div>
      </section>

      {/* Feature Grid - Uses your Dashboard Grid logic */}
      <div className="dashboard-grid">
        <div className="card">
          <h3 style={{ color: "#3b82f6" }}>✍️ Create</h3>
          <p className="text-muted">Draft content with ease. Authors can manage versions and submit works for formal review.</p>
        </div>

        <div className="card">
          <h3 style={{ color: "#3b82f6" }}>🔍 Review</h3>
          <p className="text-muted">Ensuring quality through dedicated review queues. Provide feedback and maintain standards.</p>
        </div>

        <div className="card">
          <h3 style={{ color: "#3b82f6" }}>🚀 Publish</h3>
          <p className="text-muted">One-click publishing for approved content. Seamlessly transition from draft to live.</p>
        </div>
      </div>

      {/* About Section - Simple Card */}
      <div className="card" style={{ marginTop: "40px", borderLeft: "4px solid #3b82f6" }}>
        <h3>About This Project</h3>
        <p>
          This Content Management System is designed to showcase a robust 
          <strong> Role-Based Access Control (RBAC)</strong> system. It tracks the 
          lifecycle of content through distinct stages: <em>Draft, Review, Approved, and Published.</em>
        </p>
      </div>
    </div>
  );
}

export default Home;