import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import AuthorPage from "./AuthorPage";
import ReviewerPage from "./ReviewerPage";
import PublisherPage from "./PublisherPage";
import AdminPage from "./AdminPage";
import Layout from "../components/Layout";

function Dashboard() {
  const { user } = useContext(AuthContext);

  // 1. Always check if user exists BEFORE accessing user.role
  if (!user) {
    return (
      <Layout>
        <h3>Unauthorized. Please log in.</h3>
      </Layout>
    );
  }

  console.log("Current User Role:", user.role);

  // 2. Define which component to show based on role
  let PageContent;

  const roles = Array.isArray(user.role) ? user.role : [user.role];

  if (roles.includes("ROLE_AUTHOR")) {
    PageContent = <AuthorPage />;
  } else if (roles.includes("ROLE_REVIEWER")) {
    PageContent = <ReviewerPage />;
  } else if (roles.includes("ROLE_PUBLISHER")) {
    PageContent = <PublisherPage />;
  } else if (roles.includes("ROLE_SUPER_ADMIN")) {
    PageContent = <AdminPage />;
  } else {
    PageContent = <h3>No Role Assigned</h3>;
  }

  // 3. Wrap the result in your Layout
  return (
    <Layout>
      {PageContent}
    </Layout>
  );
}

export default Dashboard;