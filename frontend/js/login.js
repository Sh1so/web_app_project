document.querySelector(".login-form").addEventListener("submit", async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);

  try {
    const response = await fetch("http://127.0.0.1:8080/login", {
      method: "POST",
      body: formData,
      credentials: "include",
    });

    if (response.ok) {
      alert("Zalogowano!");
      window.location.href = "/index.html";
      // przekieruj, np. do strony
    } else {
      alert("Blad logowania");
    }
  } catch (err) {
    console.error("Blad:", err);
  }
});
