// pages/admin.jsx
import { useState } from "react";

export default function Admin() {
  const [queryUsername, setQueryUsername] = useState("");
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState("");

  const handleQuery = async (e) => {
    e.preventDefault();
    const authHeader = localStorage.getItem("authHeader");
    if (!authHeader) {
      setError("You must be logged in as an admin.");
      return;
    }
    try {
      const res = await fetch(`http://localhost:8081/user/get/${queryUsername}`, {
        method: "GET",
        headers: {
          Authorization: authHeader,
          "Content-Type": "application/json",
        },
      });
      if (res.ok) {
        const data = await res.json();
        setUserData(data);
        setError("");
      } else {
        setError("User not found or error fetching user data.");
        setUserData(null);
      }
    } catch (err) {
      console.error("Error fetching user data", err);
      setError("An error occurred.");
      setUserData(null);
    }
  };

  return (
    <div style={styles.container}>
      <h1>Admin Panel</h1>
      <form onSubmit={handleQuery} style={styles.form}>
        <label>
          Enter Username:
          <input
            type="text"
            value={queryUsername}
            onChange={(e) => setQueryUsername(e.target.value)}
            required
            style={styles.input}
          />
        </label>
        <button type="submit" style={styles.button}>Fetch User</button>
      </form>
      {error && <p style={styles.error}>{error}</p>}
      {userData && (
        <div style={styles.userInfo}>
          <h2>User Info</h2>
          <p><strong>Username:</strong> {userData.username}</p>
          <p><strong>Score:</strong> {userData.score}</p>
          {/* You can add more details or update functionality as needed */}
        </div>
      )}
    </div>
  );
}

const styles = {
  container: { maxWidth: "600px", margin: "2rem auto", textAlign: "center" },
  form: { display: "flex", flexDirection: "column", gap: "1rem", marginBottom: "2rem" },
  input: { padding: "0.5rem", fontSize: "1rem" },
  button: { padding: "0.75rem", fontSize: "1rem", cursor: "pointer" },
  error: { color: "red" },
  userInfo: { border: "1px solid #ccc", padding: "1rem", marginTop: "1rem", borderRadius: "5px" },
};
