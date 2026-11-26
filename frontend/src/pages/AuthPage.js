import { useState } from "react";
import { login, register } from "../services/authService";

const passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,72}";

function AuthPage({ initialError = "", onAuthenticated }) {
  const [mode, setMode] = useState("login");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState(initialError);
  const [submitting, setSubmitting] = useState(false);
  const isRegistering = mode === "register";

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setSubmitting(true);

    try {
      const credentials = { email: email.trim(), password };
      const authenticatedUser = isRegistering
        ? await register(credentials)
        : await login(credentials);
      onAuthenticated(authenticatedUser);
    } catch (requestError) {
      setError(requestError.message || "No fue posible completar la solicitud.");
    } finally {
      setSubmitting(false);
    }
  }

  function changeMode(nextMode) {
    setMode(nextMode);
    setPassword("");
    setShowPassword(false);
    setError("");
  }

  return (
    <main className="auth-card">
      <div className="brand-mark" aria-hidden="true">V</div>
      <p className="eyebrow">Tu espacio personal</p>
      <h1>{isRegistering ? "Crea tu cuenta" : "Inicia sesión"}</h1>
      <p className="subtitle">
        {isRegistering
          ? "Regístrate con tu correo para comenzar en Vitalog."
          : "Ingresa a tu cuenta para continuar en Vitalog."}
      </p>

      <form className="auth-form" onSubmit={handleSubmit}>
        <div className="field">
          <label htmlFor="email">Correo electrónico</label>
          <input
            id="email"
            name="email"
            type="email"
            autoComplete="email"
            maxLength={254}
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="tu@correo.com"
            required
          />
        </div>

        <div className="field">
          <label htmlFor="password">Contraseña</label>
          <div className="password-input-wrap">
            <input
              id="password"
              name="password"
              type={showPassword ? "text" : "password"}
              autoComplete={isRegistering ? "new-password" : "current-password"}
              minLength={isRegistering ? 8 : undefined}
              maxLength={isRegistering ? 72 : undefined}
              pattern={isRegistering ? passwordPattern : undefined}
              title={isRegistering
                ? "Usa al menos 8 caracteres, con mayúscula, minúscula, número y carácter especial."
                : undefined}
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />
            <button
              className="password-visibility-toggle"
              type="button"
              aria-label={showPassword ? "Ocultar contraseña" : "Mostrar contraseña"}
              aria-pressed={showPassword}
              onClick={() => setShowPassword((isVisible) => !isVisible)}
            >
              <svg
                aria-hidden="true"
                focusable="false"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="1.8"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                {showPassword ? (
                  <>
                    <path d="M3 3l18 18" />
                    <path d="M10.6 10.6a2 2 0 002.8 2.8" />
                    <path d="M9.9 5.2A10.7 10.7 0 0112 5c6.4 0 10 7 10 7a15.7 15.7 0 01-4.1 4.9" />
                    <path d="M6.2 6.2C3.5 8 2 12 2 12s3.6 7 10 7a10.7 10.7 0 004.1-.8" />
                  </>
                ) : (
                  <>
                    <path d="M2 12s3.6-7 10-7 10 7 10 7-3.6 7-10 7S2 12 2 12z" />
                    <circle cx="12" cy="12" r="3" />
                  </>
                )}
              </svg>
            </button>
          </div>
          {isRegistering && (
            <p className="password-help">
              Mínimo 8 caracteres: incluye una mayúscula, una minúscula, un número y un carácter especial.
            </p>
          )}
        </div>

        <p className="form-message" role="alert">{error}</p>
        <button className="primary-button" type="submit" disabled={submitting}>
          {submitting
            ? "Un momento…"
            : isRegistering
              ? "Crear cuenta"
              : "Iniciar sesión"}
        </button>
      </form>

      <p className="mode-switch">
        {isRegistering ? "¿Ya tienes una cuenta?" : "¿Aún no tienes una cuenta?"}{" "}
        <button
          className="text-button"
          type="button"
          onClick={() => changeMode(isRegistering ? "login" : "register")}
        >
          {isRegistering ? "Inicia sesión" : "Crear cuenta"}
        </button>
      </p>
    </main>
  );
}

export default AuthPage;
