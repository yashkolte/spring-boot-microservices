// pages/register.jsx
import { useState } from "react";
import { useRouter } from "next/router";

export default function Register() {
  const router = useRouter();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleRegister = async (e) => {
    e.preventDefault();
    // Create user object; additional fields (such as score) could be added if needed.
    const user = { username, password };
    try {
      const res = await fetch("http://localhost:8081/user/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });
      if (res.status === 201) {
        setMessage("Registration successful. Redirecting to login...");
        setTimeout(() => router.push("/login"), 2000);
      } else {
        const errData = await res.text();
        setError("Registration failed: " + errData);
      }
    } catch (err) {
      console.error("Error during registration", err);
      setError("An error occurred. Please try again.");
    }
  };

  return (
    <div style={styles.container}>
      <h1>Register</h1>
      <form onSubmit={handleRegister} style={styles.form}>
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
        {message && <p style={styles.success}>{message}</p>}
        {error && <p style={styles.error}>{error}</p>}
        <button type="submit" style={styles.button}>Register</button>
      </form>
      <p>
        Already have an account? <a href="/login">Login here.</a>
      </p>
    </div>
  );
}

const styles = {
  container: { maxWidth: "400px", margin: "2rem auto", textAlign: "center" },
  form: { display: "flex", flexDirection: "column", gap: "1rem" },
  input: { padding: "0.5rem", fontSize: "1rem" },
  button: { padding: "0.75rem", fontSize: "1rem", cursor: "pointer" },
  success: { color: "green" },
  error: { color: "red" },
};
