/**
 * Home Page Controller
 * Renders the home/welcome page of the application
 */

/**
 * Render the home page
 */
async function renderHomePage() {
    const appContainer = document.getElementById('app-container');

    // Mock icons (replace with real SVGs or images in production)
    const icons = [
        'fa-camera', 'fa-tablet-alt', 'fa-desktop', 'fa-tv', 'fa-print', 'fa-mobile-alt',
        'fa-hdd', 'fa-laptop', 'fa-drone', 'fa-tablet', 'fa-gamepad', 'fa-laptop-code',
        'fa-headphones', 'fa-volume-up', 'fa-laptop', 'fa-mouse', 'fa-keyboard', 'fa-watch'
    ];

    // Mock featured products
    const products = [
        {
            img: 'laptop stand.webp',
            name: 'Podstawka pod laptopa',
            desc: 'Regulowana, lekka podstawka pod laptopa- poprawia ergonomię pracy i chłodzenie urządzenia.',
        },
        {
            img: 'mouse-right one.jpg',
            name: 'Bezprzewodowa myszka',
            desc: 'Profilowana mysz bezprzewodowa, stworzona z myślą o długiej pracy bez bólu nadgarstka.',
        },
        {
            img: 'mousepad.jpg',
            name: 'Podkładka pod nadgarstki',
            desc: 'Miękka, żelowa podkładka- chroni nadgarstki i zapewnia wygodę podczas pisania.',
        }
    ];

    // Mock testimonials
    const testimonials = [
        { name: 'Marta', text: 'Ekspresowa wysyłka i świetna jakość produktów. Mój nowy sprzęt do pracy sprawdza się idealnie!' },
        { name: 'Tomek', text: 'Wszystko zgodne z opisem, bardzo wygodne akcesoria. Na pewno wrócę po więcej!' },
        { name: 'Jakub', text: 'Super obsługa i szybka pomoc przy zamówieniu. Polecam każdemu, kto ceni wygodę przy pracy zdalnej.' }
    ];

    appContainer.innerHTML = `
    <header class="d-flex justify-content-between align-items-center py-3 border-bottom">
        <div class="ps-3 fw-bold fs-4">RemoTech</div>
        <nav>
            <a href="#" class="mx-3 text-dark" data-route="products">Produkty</a>
            <a href="#" class="mx-3 text-dark">Kontakt</a>
            <a href="#" class="mx-3 text-dark">O nas</a>
            <button class="btn btn-dark ms-3">LOG IN</button>
        </nav>
        <div class="pe-3">
            <a href="#" class="text-dark" data-route="cart"><i class="bi bi-cart"></i> Koszyk</a>
        </div>
    </header>

    <section class="container my-5">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h1 class="display-3 fw-bold">RemoTech</h1>
                <p class="fs-5">Tworzymy wygodne, zdrowe i praktyczne stanowiska do pracy i nauki. Sprawdź naszą selekcję akcesoriów, które zmienią Twoją codzienność!</p>
            </div>
            <div class="col-md-4 text-end d-none d-md-block">
                <a href="#" class="btn btn-outline-dark"><i class="bi bi-cart"></i> Koszyk</a>
            </div>
        </div>
        <div class="row mt-4 g-3 justify-content-center">
            ${icons.map(icon => `
                <div class="col-2 col-md-1 text-center">
                    <i class="bi bi-laptop" style="font-size:2rem;"></i>
                </div>
            `).join('')}
        </div>
    </section>

    <section class="container my-5">
        <h2 class="mb-4">Przykładowe produkty RemoTech</h2>
        <div class="row g-4">
            ${products.map(product => `
                <div class="col-md-4">
                    <div class="card h-100">
                        <img src="${product.img}" class="card-img-top" alt="${product.name}">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.desc}</p>
                            <button class="btn btn-dark w-100"><i class="bi bi-cart"></i></button>
                        </div>
                    </div>
                </div>
            `).join('')}
        </div>
    </section>

    <section class="container my-5">
        <h3 class="mb-4">Dlaczego RemoTech jest najlepszym wyborem ?</h3>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="d-flex align-items-start">
                    <span class="me-3 fs-2">→</span>
                    <div>Stawiamy na jakość i wygodę – wszystkie produkty są sprawdzone przez specjalistów, aby zapewnić Ci komfortową pracę i naukę każdego dnia.</div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="d-flex align-items-start">
                    <span class="me-3 fs-2">→</span>
                    <div>RemoTech to szybka wysyłka, przyjazna obsługa i sprawdzone akcesoria, które naprawdę zmieniają Twoje domowe biuro na lepsze.</div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="d-flex align-items-start">
                    <span class="me-3 fs-2">→</span>
                    <div>Oferujemy tylko to, z czego sami chcielibyśmy korzystać – niezawodne gadżety, które podnoszą jakość codziennej pracy.</div>
                </div>
            </div>
        </div>
        <div class="text-end mt-4">
            <button class="btn btn-dark">O NAS</button>
        </div>
    </section>

    <section class="container my-5">
        <h3 class="mb-4">Jakość, która mówi sama za siebie</h3>
        <div class="row g-4">
            ${testimonials.map(t => `
                <div class="col-md-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <p class="card-text">“${t.text}”</p>
                        </div>
                        <div class="card-footer bg-white border-0">
                            <span class="fw-bold">${t.name}</span>
                        </div>
                    </div>
                </div>
            `).join('')}
        </div>
    </section>

    <section class="bg-light py-5 mt-5">
        <div class="container d-flex flex-column flex-md-row justify-content-between align-items-center">
            <h2 class="mb-3 mb-md-0">Zostań z nami na dłużej!</h2>
            <button class="btn btn-dark">Zarejestruj się!</button>
        </div>
    </section>

    <footer class="container py-4 d-flex flex-column flex-md-row justify-content-between align-items-center">
        <div>RemoTech</div>
        <div>
            <a href="#" class="me-3"><i class="bi bi-facebook"></i></a>
            <a href="#"><i class="bi bi-instagram"></i></a>
        </div>
        <div class="mt-3 mt-md-0">
            <a href="#" class="me-3">O sklepie</a>
            <a href="#" class="me-3">Produkty</a>
            <a href="#" class="me-3">O nas</a>
            <a href="#">Koszyk</a>
        </div>
    </footer>
    `;
}