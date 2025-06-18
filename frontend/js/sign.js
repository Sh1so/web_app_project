// Dodaj debug na początku
console.log("Skrypt sign.js został załadowany");

// Sprawdź czy DOM jest gotowy
document.addEventListener("DOMContentLoaded", function () {
  console.log("DOM załadowany");

  // Sprawdź czy formularz istnieje
  const form = document.querySelector(".login-form");
  console.log("Znaleziony formularz:", form);

  if (!form) {
    console.error("Nie znaleziono formularza z klasą 'login-form'");
    return;
  }

  form.addEventListener("submit", async function (e) {
    console.log("Formularz wysłany"); // Debug
    e.preventDefault(); // nie przeładowuj strony

    const formElement = e.target;

    // Sprawdź czy wszystkie pola istnieją
    console.log("firstName element:", formElement.firstName);
    console.log("lastName element:", formElement.lastName);
    console.log("email element:", formElement.email);
    console.log("phone element:", formElement.phone);
    console.log("password element:", formElement.password);
    console.log("enabled element:", formElement.enabled);

    // Zbieramy dane z formularza
    const formData = {
      firstName: formElement.firstName ? formElement.firstName.value : "",
      lastName: formElement.lastName ? formElement.lastName.value : "",
      email: formElement.email ? formElement.email.value : "",
      phone: formElement.phone ? formElement.phone.value : "",
      password: formElement.password ? formElement.password.value : "",
      enabled: Boolean(parseInt(formElement.enabled.value)),
      roles: [
        {
          authority: "customer",
        },
      ],
    };

    console.log("Dane do wysłania:", formData);
    // Sprawdź czy wszystkie wymagane pola są wypełnione
    if (
      !formData.firstName ||
      !formData.lastName ||
      !formData.email ||
      !formData.password
    ) {
      alert("Proszę wypełnić wszystkie wymagane pola");
      return;
    }

    try {
      console.log("Wysyłanie żądania do serwera...");

      const response = await fetch("http://127.0.0.1:8080/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(formData),
      });

      console.log("Otrzymano odpowiedź:", response);
      console.log("Status:", response.status);
      console.log("Headers:", response.headers);

      // Sprawdź Content-Type odpowiedzi
      const contentType = response.headers.get("content-type");
      console.log("Content-Type:", contentType);

      if (response.ok) {
        // Sprawdź czy odpowiedź to JSON
        if (contentType && contentType.includes("application/json")) {
          const result = await response.json();
          alert("Rejestracja zakończona sukcesem!");
          console.log("Serwer zwrócił:", result);
          formElement.reset(); // Wyczyść formularz
          window.location.href = "/login.html";
        } else {
          // Jeśli nie JSON, wyświetl jako tekst
          const textResult = await response.text();
          console.log("Odpowiedź serwera (nie JSON):", textResult);
          alert("Rejestracja zakończona sukcesem!");
          formElement.reset();
          window.location.href = "/login.html";
        }
      } else {
        // Obsługa błędów HTTP
        console.log("Błąd HTTP, status:", response.status);

        if (contentType && contentType.includes("application/json")) {
          const error = await response.json();
          console.log("Błąd JSON:", error);
          alert("Błąd rejestracji: " + (error.message || "Nieznany błąd"));
        } else {
          // Jeśli błąd nie jest w formacie JSON
          const errorText = await response.text();
          console.error("Błąd serwera (HTML/tekst):", errorText);
          alert("Błąd rejestracji. Status: " + response.status);
        }
      }
    } catch (err) {
      console.error("Błąd połączenia z serwerem:", err);
      console.error("Typ błędu:", err.name);
      console.error("Komunikat błędu:", err.message);

      // Dodatkowe informacje diagnostyczne
      if (err.name === "TypeError" && err.message.includes("Failed to fetch")) {
        alert(
          "Nie można połączyć się z serwerem. Sprawdź czy serwer działa na http://localhost:8080"
        );
      } else if (err.message.includes("Unexpected token")) {
        alert(
          "Serwer zwrócił nieprawidłową odpowiedź (prawdopodobnie HTML zamiast JSON)"
        );
      } else {
        alert("Wystąpił błąd po stronie klienta: " + err.message);
      }
    }
  });
});
