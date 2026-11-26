import { useEffect, useState } from "react";
import AuthPage from "./pages/AuthPage";
import WelcomePage from "./pages/WelcomePage";
import { getCurrentUser, logout } from "./services/authService";

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [initialError, setInitialError] = useState("");

  useEffect(() => {
    let active = true;

    getCurrentUser()
      .then((currentUser) => {
        if (active) {
          setUser(currentUser);
        }
      })
      .catch((error) => {
        if (active) {
          setInitialError(error.message);
        }
      })
      .finally(() => {
        if (active) {
          setLoading(false);
        }
      });

    return () => {
      active = false;
    };
  }, []);

  async function handleLogout() {
    await logout();
    setUser(null);
  }

  if (loading) {
    return <main className="loading" role="status">Cargando Vitalog…</main>;
  }

  if (user) {
    return <WelcomePage email={user.email} onLogout={handleLogout} />;
  }

  return (
    <AuthPage
      initialError={initialError}
      onAuthenticated={(authenticatedUser) => {
        setInitialError("");
        setUser(authenticatedUser);
      }}
    />
  );
}

export default App;
