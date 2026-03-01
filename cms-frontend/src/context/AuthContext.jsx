import { createContext, useState } from "react";
import { jwtDecode } from "jwt-decode";

export const AuthContext = createContext();



export const AuthProvider = ({ children }) => {
const [user, setUser] = useState(() => {
  const token = localStorage.getItem("token");
  if (!token) return null;

  try {
    const decoded = jwtDecode(token);
    
    // CHECK IF EXPIRED: 
    // Date.now() is in ms, decoded.exp is in seconds
    if (decoded.exp * 1000 < Date.now()) {
      console.warn("Token expired, clearing storage.");
      localStorage.removeItem("token");
      return null;
    }

    return {
      username: decoded.sub,
      role: decoded.role || decoded.authorities || decoded.roles
    };
  } catch (error) {
    localStorage.removeItem("token");
    return null;
  }
});

  const login = (token) => {
    localStorage.setItem("token", token);
    const decoded = jwtDecode(token);
    
    setUser({
      username: decoded.sub,
      role: decoded.role || decoded.authorities || decoded.roles
    });
  };

  const logout = () => {
    localStorage.removeItem("token");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};