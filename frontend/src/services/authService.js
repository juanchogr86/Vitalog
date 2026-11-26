class ApiError extends Error {
  constructor(message, status) {
    super(message);
    this.name = "ApiError";
    this.status = status;
  }
}

async function request(path, options = {}) {
  const headers = new Headers(options.headers);
  headers.set("Accept", "application/json");
  if (options.body) {
    headers.set("Content-Type", "application/json");
  }

  const response = await fetch(path, {
    ...options,
    headers,
    credentials: "same-origin",
  });

  if (response.status === 204) {
    return null;
  }

  const body = response.headers.get("content-type")?.includes("application/json")
    ? await response.json()
    : null;

  if (!response.ok) {
    throw new ApiError(body?.message || `Error de solicitud (${response.status}).`, response.status);
  }

  return body;
}

async function csrfToken() {
  const response = await request("/api/auth/csrf");
  return response.token;
}

async function postWithCsrf(path, body) {
  const token = await csrfToken();
  return request(path, {
    method: "POST",
    headers: { "X-XSRF-TOKEN": token },
    body: JSON.stringify(body),
  });
}

export function register(credentials) {
  return postWithCsrf("/api/auth/register", credentials);
}

export function login(credentials) {
  return postWithCsrf("/api/auth/login", credentials);
}

export async function getCurrentUser() {
  try {
    return await request("/api/auth/me");
  } catch (error) {
    if (error instanceof ApiError && error.status === 401) {
      return null;
    }
    throw error;
  }
}

export async function logout() {
  await postWithCsrf("/api/auth/logout");
}

export function createHabit(habit) {
  return postWithCsrf("/api/habits", habit);
}

export function getHabits() {
  return request("/api/habits");
}
