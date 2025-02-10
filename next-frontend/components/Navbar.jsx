// components/Navbar.jsx
import Link from "next/link";

export default function Navbar() {
  return (
    <nav style={navStyles.nav}>
      <ul style={navStyles.ul}>
        <li style={navStyles.li}>
          <Link href="/login">
            Login
          </Link>
        </li>
        <li style={navStyles.li}>
          <Link href="/register">
            Register
          </Link>
        </li>
        <li style={navStyles.li}>
          <Link href="/quiz">
            Quiz
          </Link>
        </li>
        <li style={navStyles.li}>
          <Link href="/admin">
            Admin
          </Link>
        </li>
      </ul>
    </nav>
  );
}

const navStyles = {
  nav: {
    background: "#f5f5f5",
    padding: "1rem",
  },
  ul: {
    listStyle: "none",
    display: "flex",
    gap: "1rem",
    margin: 0,
    padding: 0,
  },
  li: {
    fontSize: "1.1rem",
  },
};
