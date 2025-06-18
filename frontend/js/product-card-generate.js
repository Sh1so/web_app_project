/**
 * Product card generation script
 */

const images = [
  "assets/monitor stand.webp",
  "assets/mouse-right one.jpg",
  "assets/mousepad.jpg",
  "assets/lamp desk.jpg",
  "assets/wireless headphones.jpg",
  "assets/laptop stand.webp",
];
let i = 0;

document.addEventListener("DOMContentLoaded", () => {
  fetch("http://127.0.0.1:8080/api/products")
    .then((response) => response.json())
    .then((products) => {
      const container = document.querySelector(".produkty-grid-js");

      products.forEach((product) => {
        const card = document.createElement("div");
        card.className = "produkty-card";

        card.innerHTML = `
                    <img src="${images[i]}" alt="${product.shortName}">
                    <h3>${product.shortName}</h3>
                    <p>${product.productDetails.description}</p>
                    <div class="produkty-bottom-row">
                        <span class="product-price">${product.price} zł</span>
                        <button class="product-card__cart" aria-label="Dodaj do koszyka" onclick="js/add-to-card.js">
                            <i class="bi bi-cart"></i>
                        </button>
                    </div>
                `;

        container.appendChild(card);
        i = i + 1;
      });
    })
    .catch((error) => {
      console.error("Błąd podczas ładowania produktów:", error);
    });
});
