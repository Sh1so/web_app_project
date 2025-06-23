function showAddProductForm(id = null) {
  const section = document.querySelector(".produkty-content");
  section.innerHTML = "";

  const formWrapper = document.createElement("div");

  formWrapper.innerHTML = `
    <section
      class="login-form-section"
    >
      <div class="login-form-title">${id ? "Edytuj produkt" : "Dodaj produkt do sklepu"}</div>
      <form class="login-form" id="productForm">
        <div class="login-form-group">
          <label for="wybor">Wybierz kategorie:</label>
          <select id="wybor" name="wybor">
            <option value="1">Sprzęt elektroniczny</option>
            <option value="2">Produkt Komplementarny</option>
          </select>
        </div>
        <div class="login-form-group">
          <label for="shortName">Krótka nazwa produktu:</label>
          <input type="text" id="shortName" name="shortName" required />
        </div>
        <div class="login-form-group">
          <label for="fullName">Pełna nazwa produktu:</label>
          <input type="text" id="fullName" name="fullName" required />
        </div>
        <div class="login-form-group">
          <label for="price">Cena detaliczna:</label>
          <input type="text" id="price" name="price" required />
        </div>
        <div class="login-form-group">
          <label for="path">Ścieżka do zdjęcia (TODO, nie działa):</label>
          <input type="text" id="path" name="path"/>
        </div>
        <div class="login-form-group">
          <label for="description">Opis produktu:</label>
          <textarea id="description" name="description" required></textarea>
        </div>
        <div class="login-form-group-btn">
          <button type="submit" class="header__login__btn">${id ? "Zapisz zmiany" : "Dodaj produkt"}</button>
          <a class="header__login" href="admin.html">Wróć</a>
        </div>
      </form>
    </section>
  `;

  section.appendChild(formWrapper);

  const form = document.querySelector("#productForm");

  if (id) {
    // pobieramy dane i uzupełniamy formularz
    fetch(`http://127.0.0.1:8080/api/products/${id}`, {
      credentials: "include"
    })
      .then(res => res.json())
      .then(data => {
        console.log("Dane z backendu:", data); // ← SPRAWDŹ TUTAJ
        document.querySelector("#shortName").value = data.shortName || "";
        document.querySelector("#fullName").value = data.productDetails.fullName || "";
        document.querySelector("#price").value = data.price || "";
        document.querySelector("#description").value = data.productDetails.description || "";
        document.querySelector("#wybor").value = data.categoryId || "1";
      });
  }

  form.addEventListener("submit", function (e) {
    e.preventDefault();

    const payload = {
      shortName: document.querySelector("#shortName").value,
      price: document.querySelector("#price").value,
      productDetails: {
        fullName: document.querySelector("#fullName").value,
        description: document.querySelector("#description").value,
      },
      category: {
        id: document.querySelector("#wybor").value
      }
    };

    const url = id
      ? `http://127.0.0.1:8080/api/products/${id}`
      : "http://127.0.0.1:8080/api/products";

    const method = id ? "PUT" : "POST";

    fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
      credentials: "include"
    })
      .then(res => {
        if (res.ok) {
          alert(id ? "Zaktualizowano!" : "Dodano produkt!");
          window.location.href = "admin.html";
        } else {
          alert("Błąd przy zapisie.");
        }
      })
      .catch(err => {
        console.error("Błąd:", err);
        alert("Błąd połączenia z serwerem.");
      });
  });
}

// --- Funkcja pobierająca produkty z API ---
async function fetchProducts() {
  try {
    const response = await fetch("http://127.0.0.1:8080/api/products", {
      credentials: "include",
    });
    if (!response.ok) throw new Error("Błąd sieci");
    const data = await response.json();
    generateTable(data);
  } catch (error) {
    console.error("Błąd podczas pobierania danych:", error);
  }
}

// --- Funkcja tworząca tabelę z produktami ---
function generateTable(data) {
  const section = document.querySelector(".produkty-content");
  section.innerHTML = "";

  const table = document.createElement("table");
  table.classList.add("admin-table");

  // Nagłówek tabeli
  const thead = document.createElement("thead");
  thead.innerHTML = `
    <tr>
      <th>ID</th>
      <th>Nazwa</th>
      <th>Cena</th>
      <th>Data dodania</th>
      <th>Data edycji</th>
      <th>Edytuj</th>
      <th>Usuń</th>
    </tr>
  `;

  const tbody = document.createElement("tbody");

  data.forEach((produkt) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${produkt.id}</td>
      <td>${produkt.shortName}</td>
      <td>${produkt.price}</td>
      <td>${produkt.createdAt}</td>
      <td>${produkt.updatedAt}</td>
      <td><a href="#" class="edit-link" data-id="${produkt.id}">Edytuj</a></td>
      <td><a href="#" class="delete-link" data-id="${produkt.id}">Usuń</a></td>
    `;
    tbody.appendChild(row);
  });

  table.appendChild(thead);
  table.appendChild(tbody);
  section.appendChild(table);

  // Dodaj przycisk Wróć pod tabelą
  const backButton = document.createElement("a");
  backButton.textContent = "Wróć";
  backButton.classList.add("header__login__table");
  backButton.href = "admin.html";
  section.appendChild(backButton);

  attachDeleteHandlers();
  const editLinks = document.querySelectorAll(".edit-link");
  editLinks.forEach(link => {
  link.addEventListener("click", (e) => {
    e.preventDefault();
    const id = link.dataset.id;
    showAddProductForm(id); // 
  });
});
}

// --- Funkcja dodająca event listenery do linków Usuń ---
function attachDeleteHandlers() {
  const deleteLinks = document.querySelectorAll(".delete-link");
  deleteLinks.forEach((link) => {
    link.addEventListener("click", async (e) => {
      e.preventDefault();
      const id = link.dataset.id;

      if (confirm("Na pewno chcesz usunąć ten produkt?")) {
        try {
          const res = await fetch(`http://127.0.0.1:8080/api/products/${id}`, {
            method: "DELETE",
            credentials: "include",
          });

          if (res.ok) {
            await fetchProducts(); // odśwież listę produktów
          } else {
            alert("Błąd przy usuwaniu.");
          }
        } catch (err) {
          console.error("Błąd:", err);
          alert("Nie udało się połączyć z serwerem.");
        }
      }
    });
  });
}

async function loadDashboard() {
  const dashboard = document.querySelector(".produkty-content");
  dashboard.innerHTML = ``;
  dashboard.classList.add("dashboard");

  try {
    const [productsRes, usersRes] = await Promise.all([
      fetch("http://127.0.0.1:8080/api/products", {
        credentials: "include"
      }),
      fetch("http://127.0.0.1:8080/api/users", {
        credentials: "include"
      })
    ]);

    const products = await productsRes.json();
    const users = await usersRes.json();

    // KPI cards
    const kpis = [
      { title: "Produkty", value: products.length },
      { title: "Klienci", value: users.length }
    ];

    kpis.forEach(kpi => {
      const card = document.createElement("div");
      card.classList.add("kpi-card");
      card.innerHTML = `
        <div class="kpi-title">${kpi.title}</div>
        <div class="kpi-value">${kpi.value}</div>
      `;
      dashboard.appendChild(card);
    });

    // Wykres
    const chartContainer = document.createElement("div");
    chartContainer.classList.add("chart-container");

    const canvas = document.createElement("canvas");
    canvas.classList.add("chart-canvas");

    chartContainer.appendChild(canvas);
    dashboard.appendChild(chartContainer);

    // Dane do wykresu
    const grouped = {};
    products.forEach(p => {
      const date = new Date(p.createdAt).toISOString().split("T")[0];
      grouped[date] = (grouped[date] || 0) + 1;
    });

    const labels = Object.keys(grouped).sort();
    const values = labels.map(date => grouped[date]);

    const ctx = canvas.getContext("2d");
    new Chart(ctx, {
      type: "line",
      data: {
        labels: labels,
        datasets: [{
          label: "Liczba produktów (dodanych)",
          data: values,
          fill: false,
          borderColor: "#3e95cd",
          tension: 0.2,
          pointBackgroundColor: "#3e95cd",
          pointRadius: 4
        }]
      },
      options: {
        responsive: true,
        scales: {
          x: { title: { display: true, text: "Data" } },
          y: { beginAtZero: true, title: { display: true, text: "Produkty" }, ticks: { precision: 0 } }
        },
        plugins: {
          legend: { display: true }
        }
      }
    });

  } catch (err) {
    console.error("Błąd podczas ładowania dashboardu:", err);
  }
}

// --- Podpięcie eventów do przycisków ---
document.querySelector(".button-admin-1").addEventListener("click", () => {
  showAddProductForm();
});

document.querySelector(".button-admin-2").addEventListener("click", () => {
  fetchProducts();
});

document.querySelector(".button-admin-3").addEventListener("click", () => {
  loadDashboard();
});