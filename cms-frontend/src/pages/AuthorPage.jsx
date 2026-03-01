import { useEffect, useState } from "react";
import API from "../api/api";

function AuthorPage() {

  const [title, setTitle] = useState("");
  const [body, setBody] = useState("");
  const [contents, setContents] = useState([]);

  // Load my drafts
  const fetchMyContent = async () => {
    const res = await API.get("/content/my");
    setContents(res.data);
  };

  useEffect(() => {
    fetchMyContent();
  }, []);

  // Create Draft
  const createDraft = async () => {
    await API.post("/content/create", {
      title,
      body
    });

    setTitle("");
    setBody("");
    fetchMyContent();
  };

  // Update Draft
  const updateDraft = async (id) => {
    await API.put(`/content/${id}`, {
      title,
      body
    });

    setTitle("");
    setBody("");
    fetchMyContent();
  };

  // Submit for Review
  const submitForReview = async (id) => {
    await API.put(
      `/workflow/${id}/transition?targetStatus=REVIEW`
    );

    fetchMyContent();
  };

  return (
    <div className="container">
      <h2>Author Dashboard</h2>
      
      <div className="card" style={{maxWidth: '600px'}}>
        <h3>Create New Draft</h3>
        <input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
        <textarea placeholder="Write your content here..." rows="4" value={body} onChange={(e) => setBody(e.target.value)} />
        <button className="btn-primary" onClick={createDraft}>Create Draft</button>
      </div>

      <h3 style={{marginTop: '2rem'}}>My Content Library</h3>
      <div className="dashboard-grid">
        {contents.map((content) => (
          <div key={content.id} className="card">
            <span className={`badge status-${content.status.toLowerCase()}`}>{content.status}</span>
            <h4 style={{margin: '10px 0'}}>{content.title}</h4>
            <p className="text-muted">Version: {content.version}</p>
            
            {content.status === "DRAFT" && (
              <div style={{display: 'flex', gap: '10px', marginTop: '15px'}}>
                <button className="btn-primary" onClick={() => updateDraft(content.id)}>Update</button>
                <button className="btn-primary" style={{background: '#10b981'}} onClick={() => submitForReview(content.id)}>Submit</button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default AuthorPage;