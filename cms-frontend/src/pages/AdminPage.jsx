import { useEffect, useState } from "react";
import API from "../api/api";

function AdminPage() {

  const [users, setUsers] = useState([]);
  const [auditLogs, setAuditLogs] = useState([]);

  const roles = [
    "ROLE_AUTHOR",
    "ROLE_REVIEWER",
    "ROLE_EDITOR",
    "ROLE_PUBLISHER",
    "ROLE_SUPER_ADMIN"
  ];

  // Load users
  const fetchUsers = async () => {
    const res = await API.get("/admin/users");
    setUsers(res.data);
  };

  // Load audit logs
  const fetchAuditLogs = async () => {
    const res = await API.get("/admin/audit");
    setAuditLogs(res.data);
  };

  useEffect(() => {
    fetchUsers();
    fetchAuditLogs();
  }, []);

  // Change user role
  const changeRole = async (userId, role) => {
    await API.put(`/admin/users/${userId}/role`, {
      role: role
    });

    fetchUsers();
  };

  // Delete content
  const deleteContent = async (contentId) => {
    await API.delete(`/admin/content/${contentId}`);
    alert("Content deleted");
  };

  return (
    <div className="container">
      <h2>Super Admin Dashboard</h2>
      
      <h3>User Management</h3>
      <div className="dashboard-grid">
        {users.map(user => (
          <div key={user.id} className="card">
            <p><strong>{user.username}</strong></p>
            <p className="text-muted">Current Role: <span className="badge status-approved">{user.role}</span></p>
            <label>Change Role:</label>
            <select defaultValue={user.role} onChange={(e) => changeRole(user.id, e.target.value)}>
              {roles.map(role => <option key={role} value={role}>{role}</option>)}
            </select>
          </div>
        ))}
      </div>

      <h3 style={{marginTop: '3rem'}}>Recent Audit Logs</h3>
      <div className="card" style={{padding: '0', overflowX: 'auto'}}>
        <table style={{width: '100%', borderCollapse: 'collapse'}}>
          <thead style={{background: '#f8fafc'}}>
            <tr>
              <th style={{padding: '12px', textAlign: 'left'}}>User</th>
              <th style={{padding: '12px', textAlign: 'left'}}>Action</th>
              <th style={{padding: '12px', textAlign: 'left'}}>Timestamp</th>
            </tr>
          </thead>
          <tbody>
            {auditLogs.map(log => (
              <tr key={log.id} style={{borderTop: '1px solid #e2e8f0'}}>
                <td style={{padding: '12px'}}>{log.username}</td>
                <td style={{padding: '12px'}}>{log.action}</td>
                <td style={{padding: '12px', fontSize: '0.8rem', color: '#64748b'}}>{log.timestamp}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AdminPage;