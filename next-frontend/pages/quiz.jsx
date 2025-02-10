// pages/quiz.jsx
import { useState, useEffect } from "react";
import { useRouter } from "next/router";

export default function Quiz() {
  const router = useRouter();
  // Note: since localStorage is only available in the browser,
  // we check for window existence before reading.
  const authHeader =
    typeof window !== "undefined" ? localStorage.getItem("authHeader") : null;
  const username =
    typeof window !== "undefined" ? localStorage.getItem("username") : null;

  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({});
  const [result, setResult] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  // Fetch quiz questions from the backend when the component mounts
  useEffect(() => {
    async function fetchQuestions() {
      try {
        const res = await fetch("http://localhost:8082/quiz/questions", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        if (res.ok) {
          const data = await res.json();
          setQuestions(data);
          setLoading(false);
        } else {
          const errText = await res.text();
          setError("Error fetching questions: " + errText);
          setLoading(false);
        }
      } catch (err) {
        console.error("Error fetching questions", err);
        setError("An error occurred while fetching questions.");
        setLoading(false);
      }
    }

    fetchQuestions();
  }, []);

  const handleChange = (id, value) => {
    setAnswers((prev) => ({ ...prev, [id]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!authHeader || !username) {
      setError("You must be logged in to submit the quiz.");
      router.push("/login");
      return;
    }

    // Build query parameters from answers manually so that the URL looks exactly as desired.
    // This approach encodes the values properly.
    const queryString = Object.entries(answers)
      .map(([key, value]) => `${encodeURIComponent(key)}=${encodeURIComponent(value)}`)
      .join("&");

    // Log the URL for debugging purposes
    const url = `http://localhost:8082/quiz/submit?${queryString}`;
    console.log("Submitting quiz to URL:", url);

    try {
      const res = await fetch(url, {
        method: "POST",
        headers: {
          Authorization: authHeader,
        },
      });
      if (res.ok) {
        const text = await res.text();
        setResult(text);
        setError("");
      } else {
        const errText = await res.text();
        setError("Error submitting quiz: " + errText);
      }
    } catch (err) {
      console.error("Error submitting quiz", err);
      setError("An error occurred while submitting the quiz.");
    }
  };

  if (loading) {
    return (
      <div style={styles.container}>
        <h1>Loading Quiz...</h1>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <h1>Quiz</h1>
      <form onSubmit={handleSubmit} style={styles.form}>
        {questions.map((q) => (
          <div key={q.id} style={styles.questionBlock}>
            <p>
              <strong>{q.question}</strong>
            </p>
            <input
              type="text"
              placeholder="Your answer"
              onChange={(e) => handleChange(q.id, e.target.value)}
              style={styles.input}
            />
          </div>
        ))}
        {error && <p style={styles.error}>{error}</p>}
        {result && <p style={styles.result}>{result}</p>}
        <button type="submit" style={styles.button}>
          Submit Quiz
        </button>
      </form>
    </div>
  );
}

const styles = {
  container: { maxWidth: "600px", margin: "2rem auto", textAlign: "center" },
  form: { display: "flex", flexDirection: "column", gap: "1rem" },
  questionBlock: {
    border: "1px solid #eee",
    padding: "1rem",
    borderRadius: "5px",
  },
  input: { padding: "0.5rem", fontSize: "1rem", width: "100%" },
  button: { padding: "0.75rem", fontSize: "1rem", cursor: "pointer" },
  error: { color: "red" },
  result: { color: "green" },
};
