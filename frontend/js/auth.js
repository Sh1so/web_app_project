// auth.js - dodaj ten plik do js/
class AuthService {
  // Sprawdź czy użytkownik jest zalogowany i pobierz jego dane
  static async getCurrentUser() {
    try {
      const response = await fetch("http://127.0.0.1:8080/api/users/current", {
        method: "GET",
        credentials: "include", // Ważne dla sesji
      });

      if (response.ok) {
        return await response.json();
      }
      return null;
    } catch (error) {
      console.error("Błąd pobierania danych użytkownika:", error);
      return null;
    }
  }

  // Wyloguj użytkownika
  static async logout() {
    try {
      const response = await fetch("http://127.0.0.1:8080/logout", {
        method: "POST",
        credentials: "include",
      });

      if (response.ok) {
        // Odśwież stronę lub przekieruj
        window.location.href = "/index.html";
      }
    } catch (error) {
      console.error("Błąd wylogowania:", error);
    }
  }

  static async updateHeader() {
    const user = await this.getCurrentUser();
    const headerActions = document.querySelector(".header__actions");

    if (!user || !headerActions) {
      // Użytkownik niezalogowany lub brak elementu
      headerActions.innerHTML = `
      <a href="login.html" class="header__login">Zaloguj się</a>
    `;
      return;
    }

    // Wyczyść zawartość
    headerActions.innerHTML = "";

    // Powitanie
    const welcomeSpan = document.createElement("span");
    welcomeSpan.style.marginRight = "15px";
    welcomeSpan.style.fontWeight = "700";
    welcomeSpan.textContent = `Witaj, ${user.firstName}!`;
    headerActions.appendChild(welcomeSpan);

    // Panel administracyjny – jeśli rola to "admin"
    const isAdmin =
      Array.isArray(user.roles) &&
      user.roles.some((role) => role.authority === "admin");

    if (isAdmin) {
      const adminButton = document.createElement("a");
      adminButton.href = "/admin.html";
      adminButton.textContent = "Panel administracyjny";
      adminButton.classList.add("header__login", "admin-button");
      adminButton.style.marginRight = "10px";
      headerActions.appendChild(adminButton);
    }

    // Przycisk wylogowania – zawsze jeśli użytkownik zalogowany
    const logoutButton = document.createElement("a");
    logoutButton.classList.add("header__login");
    logoutButton.textContent = "Wyloguj";
    logoutButton.onclick = () => AuthService.logout();
    headerActions.appendChild(logoutButton);
  }
}

// Wywołaj przy ładowaniu każdej strony
document.addEventListener("DOMContentLoaded", () => {
  AuthService.updateHeader();
});

// Dodaj do istniejącej funkcji loginUser w kodzie logowania
async function loginUser(username, password) {
  try {
    const response = await fetch("http://127.0.0.1:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      credentials: "include", // Ważne!
      body: `username=${encodeURIComponent(
        username
      )}&password=${encodeURIComponent(password)}`,
    });

    const data = await response.json();

    if (response.ok && data.status === "success") {
      // Po udanym logowaniu przekieruj na stronę produktów
      window.location.href = "/produkty.html";
    } else {
      console.error("Login failed:", data.error);
      alert("Błąd logowania: " + data.error);
    }
  } catch (error) {
    console.error("Network error:", error);
    alert("Błąd sieci. Spróbuj ponownie.");
  }
}
