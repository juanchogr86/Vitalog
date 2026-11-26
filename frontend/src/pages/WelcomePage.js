import { useEffect, useRef, useState } from "react";
import { createHabit, getHabits } from "../services/authService";

const weekDays = [
  ["MONDAY", "Lunes"],
  ["TUESDAY", "Martes"],
  ["WEDNESDAY", "Miércoles"],
  ["THURSDAY", "Jueves"],
  ["FRIDAY", "Viernes"],
  ["SATURDAY", "Sábado"],
  ["SUNDAY", "Domingo"],
];

function WelcomePage({ email, onLogout }) {
  const [name, setName] = useState("");
  const [durationDays, setDurationDays] = useState(66);
  const [frequency, setFrequency] = useState("DAILY");
  const [selectedDays, setSelectedDays] = useState([]);
  const [habits, setHabits] = useState([]);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const createdDuringLoad = useRef(false);

  useEffect(() => {
    let active = true;
    getHabits()
      .then((items) => {
        if (active && !createdDuringLoad.current) setHabits(items);
      })
      .catch((requestError) => {
        if (active) setError(requestError.message);
      });
    return () => {
      active = false;
    };
  }, []);

  function toggleDay(day) {
    setSelectedDays((current) =>
      current.includes(day)
        ? current.filter((item) => item !== day)
        : [...current, day]);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setSuccess("");

    if (frequency === "WEEKLY" && selectedDays.length === 0) {
      setError("Selecciona al menos un día para la frecuencia semanal.");
      return;
    }

    setSubmitting(true);
    createdDuringLoad.current = true;
    try {
      const habit = await createHabit({
        name: name.trim(),
        durationDays,
        frequency,
        weeklyDays: frequency === "WEEKLY" ? selectedDays : [],
      });
      setHabits((current) => [habit, ...current]);
      setName("");
      setDurationDays(66);
      setFrequency("DAILY");
      setSelectedDays([]);
      setSuccess("Hábito creado correctamente.");
    } catch (requestError) {
      setError(requestError.message || "No fue posible crear el hábito.");
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="welcome-card habit-card">
      <div className="welcome-header">
        <div>
          <p className="eyebrow">Sesión iniciada</p>
          <p>Bienvenido a vitalog</p>
          <h1>Mis hábitos</h1>
          <p>Cuenta: <strong>{email}</strong></p>
        </div>
        <button className="secondary-button" type="button" onClick={onLogout}>
          Cerrar sesión
        </button>
      </div>

      <form className="auth-form habit-form" onSubmit={handleSubmit}>
        <div className="field">
          <label htmlFor="habit-name">Nombre del hábito</label>
          <input
            id="habit-name"
            type="text"
            maxLength={100}
            value={name}
            onChange={(event) => setName(event.target.value)}
            placeholder="Ej. Meditar 10 minutos"
            required
          />
        </div>

        <div className="field">
          <label htmlFor="habit-duration">Duración del seguimiento (días)</label>
          <input
            id="habit-duration"
            type="number"
            min="1"
            max="3650"
            value={durationDays}
            onChange={(event) => setDurationDays(Number(event.target.value))}
            required
          />
        </div>

        <fieldset className="frequency-field">
          <legend>Frecuencia</legend>
          <label>
            <input
              type="radio"
              name="frequency"
              value="DAILY"
              checked={frequency === "DAILY"}
              onChange={() => setFrequency("DAILY")}
            />
            Diaria
          </label>
          <label>
            <input
              type="radio"
              name="frequency"
              value="WEEKLY"
              checked={frequency === "WEEKLY"}
              onChange={() => setFrequency("WEEKLY")}
            />
            Semanal
          </label>
        </fieldset>

        {frequency === "WEEKLY" && (
          <fieldset className="week-days-field">
            <legend>Días de la semana</legend>
            <div className="week-days">
              {weekDays.map(([value, label]) => (
                <label key={value}>
                  <input
                    type="checkbox"
                    checked={selectedDays.includes(value)}
                    onChange={() => toggleDay(value)}
                  />
                  {label}
                </label>
              ))}
            </div>
          </fieldset>
        )}

        <p className="form-message" role="alert">{error}</p>
        {success && <p className="success-message" role="status">{success}</p>}
        <button className="primary-button" type="submit" disabled={submitting}>
          {submitting ? "Guardando…" : "Crear hábito"}
        </button>
      </form>

      <section className="habit-list" aria-labelledby="habit-list-title">
        <h2 id="habit-list-title">Hábitos creados</h2>
        {habits.length === 0 ? (
          <p className="empty-state">Aún no has creado hábitos.</p>
        ) : (
          <ul>
            {habits.map((habit) => (
              <li key={habit.id}>
                <strong>{habit.name}</strong>
                <span>
                  {habit.frequency === "DAILY" ? "Diario" : "Semanal"} · {habit.durationDays} días
                </span>
              </li>
            ))}
          </ul>
        )}
      </section>
    </main>
  );
}

export default WelcomePage;
