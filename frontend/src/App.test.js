import { renderToStaticMarkup } from "react-dom/server";
import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import AuthPage from "./pages/AuthPage";
import WelcomePage from "./pages/WelcomePage";
import { createHabit, getHabits, register } from "./services/authService";

jest.mock("./services/authService", () => ({
  login: jest.fn(),
  register: jest.fn(),
  createHabit: jest.fn(),
  getHabits: jest.fn(),
}));

test("renders the initial welcome message", () => {
  expect(renderToStaticMarkup(<WelcomePage email="usuario@example.com" />))
    .toContain("Bienvenido a vitalog");
});

test("toggles password visibility without changing its value", () => {
  render(<AuthPage onAuthenticated={jest.fn()} />);
  const passwordInput = screen.getByLabelText("Contraseña");
  const visibilityButton = screen.getByRole("button", { name: "Mostrar contraseña" });

  fireEvent.change(passwordInput, { target: { value: "Secret123!" } });
  expect(visibilityButton.querySelector("svg")).not.toBeNull();
  fireEvent.click(visibilityButton);
  expect(passwordInput.type).toBe("text");
  expect(passwordInput.value).toBe("Secret123!");

  fireEvent.click(screen.getByRole("button", { name: "Ocultar contraseña" }));
  expect(passwordInput.type).toBe("password");
  expect(passwordInput.value).toBe("Secret123!");
});

test("registers with email and password then notifies the authenticated user", async () => {
  const user = { email: "usuario@example.com" };
  const onAuthenticated = jest.fn();
  register.mockResolvedValue(user);

  render(<AuthPage onAuthenticated={onAuthenticated} />);
  fireEvent.click(screen.getByRole("button", { name: "Crear cuenta" }));

  fireEvent.change(screen.getByLabelText("Correo electrónico"), {
    target: { value: "  usuario@example.com " },
  });
  fireEvent.change(screen.getByLabelText("Contraseña"), {
    target: { value: "weakpass" },
  });
  expect(screen.getByLabelText("Contraseña").checkValidity()).toBe(false);

  fireEvent.change(screen.getByLabelText("Contraseña"), {
    target: { value: "StrongPass1!" },
  });
  expect(screen.getByLabelText("Contraseña").checkValidity()).toBe(true);
  fireEvent.click(screen.getByRole("button", { name: "Crear cuenta" }));

  await waitFor(() => {
    expect(register).toHaveBeenCalledWith({
      email: "usuario@example.com",
      password: "StrongPass1!",
    });
  });
  expect(onAuthenticated).toHaveBeenCalledWith(user);
});

test("rejects a weekly habit until at least one day is selected", () => {
  getHabits.mockResolvedValue([]);

  render(<WelcomePage email="usuario@example.com" onLogout={jest.fn()} />);
  fireEvent.change(screen.getByLabelText("Nombre del hábito"), {
    target: { value: "Correr" },
  });
  fireEvent.click(screen.getByLabelText("Semanal"));
  fireEvent.click(screen.getByRole("button", { name: "Crear hábito" }));

  expect(screen.getByRole("alert").textContent)
    .toContain("Selecciona al menos un día");
  expect(createHabit).not.toHaveBeenCalled();
});

test("creates a weekly habit with selected days", async () => {
  getHabits.mockResolvedValue([]);
  createHabit.mockResolvedValue({
    id: "habit-1",
    name: "Correr",
    durationDays: 66,
    frequency: "WEEKLY",
    weeklyDays: ["MONDAY", "FRIDAY"],
  });

  render(<WelcomePage email="usuario@example.com" onLogout={jest.fn()} />);
  fireEvent.change(screen.getByLabelText("Nombre del hábito"), {
    target: { value: "Correr" },
  });
  fireEvent.click(screen.getByLabelText("Semanal"));
  fireEvent.click(screen.getByLabelText("Lunes"));
  fireEvent.click(screen.getByLabelText("Viernes"));
  fireEvent.click(screen.getByRole("button", { name: "Crear hábito" }));

  await waitFor(() => {
    expect(createHabit).toHaveBeenCalledWith({
      name: "Correr",
      durationDays: 66,
      frequency: "WEEKLY",
      weeklyDays: ["MONDAY", "FRIDAY"],
    });
  });
});
