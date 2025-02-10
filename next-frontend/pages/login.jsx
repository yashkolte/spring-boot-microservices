// pages/login.jsx
import { useState } from "react";
import { useRouter } from "next/router";

export default function Login() {
  const router = useRouter();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Build Basic Auth header
    const authHeader = "Basic " + btoa(`${username}:${password}`);
    try {
      const res = await fetch(`http://localhost:8081/user/get/${username}`, {
        method: "GET",
        headers: {
          Authorization: authHeader,
          "Content-Type": "application/json",
        },
      });

      if (!res.ok) {
        setError("Invalid credentials or user not found.");
        return;
      }
      // If user exists, save auth header in local storage for later use
      const userData = await res.json();
      localStorage.setItem("authHeader", authHeader);
      localStorage.setItem("username", username);
      router.push("/quiz");
    } catch (err) {
      console.error("Error during login", err);
      setError("An error occurred. Please try again.");
    }
  };

  return (
    <div style={styles.container}>
      <h1>Login</h1>
      <form onSubmit={handleSubmit} style={styles.form}>
        <label>
          Username:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={styles.input}
          />
        </label>
        <label>
          Password:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={styles.input}
          />
        </label>
        {error && <p style={styles.error}>{error}</p>}
        <button type="submit" style={styles.button}>Login</button>
      </form>
      <p>
        Don't have an account? <a href="/register">Register here.</a>
      </p>
    </div>
  );
}

const styles = {
  container: { maxWidth: "400px", margin: "2rem auto", textAlign: "center" },
  form: { display: "flex", flexDirection: "column", gap: "1rem" },
  input: { padding: "0.5rem", fontSize: "1rem" },
  button: { padding: "0.75rem", fontSize: "1rem", cursor: "pointer" },
  error: { color: "red" },
};
