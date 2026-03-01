import { useEffect, useState } from "react";
import API from "../api/api";

function ReviewerPage() {

  const [queue, setQueue] = useState([]);
  const [feedback, setFeedback] = useState({});

  // Load review queue
  const fetchQueue = async () => {
    const res = await API.get("/review/queue");
    setQueue(res.data);
  };

  useEffect(() => {
    fetchQueue();
  }, []);

  // Handle feedback input change
  const handleFeedbackChange = (id, value) => {
    setFeedback(prev => ({
      ...prev,
      [id]: value
    }));
  };

  // Approve / Reject
  const reviewContent = async (id, decision) => {
    await API.post(`/review/${id}`, {
      feedback: feedback[id] || "",
      decision: decision
    });

    fetchQueue();
  };

  return (
    <div className="container">
      <h2>Reviewer Dashboard</h2>
      <div className="dashboard-grid">
        {queue.map((content) => (
          <div key={content.id} className="card">
            <span className="badge status-review">Pending Review</span>
            <h4>{content.title}</h4>
            <div style={{background: '#f8fafc', padding: '10px', borderRadius: '8px', fontSize: '0.9rem', marginBottom: '10px'}}>
               {content.body}
            </div>
            
            <textarea 
              placeholder="Feedback for author..." 
              onChange={(e) => handleFeedbackChange(content.id, e.target.value)}
            />
            
            <div style={{display: 'flex', gap: '10px'}}>
              <button className="btn-primary" style={{flex: 1, background: '#22c55e'}} onClick={() => reviewContent(content.id, "APPROVED")}>Approve</button>
              <button className="btn-danger" style={{flex: 1}} onClick={() => reviewContent(content.id, "REJECTED")}>Reject</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ReviewerPage;