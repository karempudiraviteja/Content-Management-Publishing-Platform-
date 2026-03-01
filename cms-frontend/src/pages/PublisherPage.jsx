import { useEffect, useState } from "react";
import API from "../api/api";

function PublisherPage() {
  const [approvedContent, setApprovedContent] = useState([]);

  // Fetch approved content
  const fetchApproved = async () => {
    try {
      const all = await API.get("/content/approved");
      // Filtering to ensure we only show items ready for the final push
      const filtered = all.data.filter((c) => c.status === "APPROVED");
      setApprovedContent(filtered);
    } catch (err) {
      console.error("Failed to fetch content", err);
    }
  };

  useEffect(() => {
    fetchApproved();
  }, []);

  // Publish transition
  const publishContent = async (id) => {
    try {
      await API.put(`/workflow/${id}/transition?targetStatus=PUBLISHED`);
      fetchApproved(); // Refresh list
    } catch (err) {
      alert("Publishing failed");
    }
  };

  return (
    <div className="container">
      <div className="dashboard-header">
        <h2>Publisher Dashboard</h2>
        <p className="text-muted">Review and go-live with approved content</p>
      </div>

      {approvedContent.length === 0 ? (
        <div className="card" style={{ textAlign: "center", padding: "3rem" }}>
          <p className="text-muted">No approved content is currently waiting to be published.</p>
        </div>
      ) : (
        <div className="dashboard-grid">
          {approvedContent.map((content) => (
            <div key={content.id} className="card">
              <div style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start" }}>
                <span className="badge status-approved">Approved</span>
                <span className="text-muted" style={{ fontSize: "0.8rem" }}>v{content.version}</span>
              </div>
              
              <h4 style={{ margin: "15px 0 10px 0", fontSize: "1.2rem" }}>{content.title}</h4>
              
              <p className="text-muted" style={{ fontSize: "0.9rem", marginBottom: "20px" }}>
                This content has passed review and is ready for public viewing.
              </p>

              <button 
                className="btn-primary" 
                style={{ width: "100%", background: "#8b5cf6" }} // Distinct purple for Publishing
                onClick={() => publishContent(content.id)}
              >
                🚀 Publish to Site
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default PublisherPage;