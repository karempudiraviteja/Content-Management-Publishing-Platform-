import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "./layout.css";

function Layout({ children }) {

  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="layout-container">

      <nav className="navbar">
        <div className="logo">CMS Platform</div>

        <div className="nav-right">
          <span className="user-info">
            {user?.username} ({user?.role})
          </span>

          <button className="logout-btn" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>

      <div className="page-content">
        {children}
      </div>

    </div>
  );
}

export default Layout;