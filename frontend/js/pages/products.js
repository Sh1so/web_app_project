async function renderProductsPage() {
    const appContainer = document.getElementById('app-container');
    
    try {
        // Get URL parameters for filtering
        const urlParams = new URLSearchParams(window.location.search);
        const categoryId = urlParams.get('category');
        const searchQuery = urlParams.get('search');

        // Fetch products and categories
        const [products, categories] = await Promise.all([
            ApiService.getAllProducts(),
            ApiService.getAllCategories()
        ]);

        // Filter products if needed
        let filteredProducts = [...products];
        if (categoryId) {
            filteredProducts = filteredProducts.filter(p => p.category_id === parseInt(categoryId));
        }
        if (searchQuery) {
            filteredProducts = filteredProducts.filter(p => 
                p.short_name.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }

        // Create the products page content
        appContainer.innerHTML = `
            <div class="row">
                <!-- Filters Sidebar -->
                <div class="col-md-3">
                    <div class="card mb-4">
                        <div class="card-body">
                            <h5 class="card-title">Filters</h5>
                            
                            <!-- Search -->
                            <div class="mb-3">
                                <label for="searchInput" class="form-label">Search</label>
                                <input type="text" class="form-control" id="searchInput" 
                                       placeholder="Search products..." value="${searchQuery || ''}">
                            </div>

                            <!-- Categories -->
                            <div class="mb-3">
                                <label class="form-label">Categories</label>
                                <div class="list-group">
                                    <a href="#" data-route="products" 
                                       class="list-group-item list-group-item-action ${!categoryId ? 'active' : ''}">
                                        All Categories
                                    </a>
                                    ${categories.map(category => `
                                        <a href="#" data-route="products" data-category="${category.category_id}"
                                           class="list-group-item list-group-item-action ${categoryId == category.category_id ? 'active' : ''}">
                                            ${category.name}
                                        </a>
                                    `).join('')}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Products Grid -->
                <div class="col-md-9">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h2>Products</h2>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-primary sort-btn" data-sort="name">
                                Name
                            </button>
                            <button type="button" class="btn btn-outline-primary sort-btn" data-sort="price">
                                Price
                            </button>
                        </div>
                    </div>

                    <div class="row" id="products-grid">
                        ${filteredProducts.map(product => `
                            <div class="col-md-4 mb-4">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <h5 class="card-title">${product.short_name}</h5>
                                        <p class="card-text">$${product.price.toFixed(2)}</p>
                                        <div class="d-flex justify-content-between">
                                            <button class="btn btn-primary view-product" 
                                                    data-product-id="${product.product_id}">
                                                View Details
                                            </button>
                                            <button class="btn btn-outline-primary add-to-cart" 
                                                    data-product-id="${product.product_id}">
                                                <i class="bi bi-cart-plus"></i>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `).join('')}
                    </div>

                    ${filteredProducts.length === 0 ? `
                        <div class="alert alert-info">
                            No products found matching your criteria.
                        </div>
                    ` : ''}
                </div>
            </div>
        `;

        // Add event listeners
        document.getElementById('searchInput').addEventListener('input', debounce((e) => {
            const search = e.target.value;
            navigateTo('products', { search });
        }, 300));

        document.querySelectorAll('.sort-btn').forEach(button => {
            button.addEventListener('click', (e) => {
                const sortBy = e.target.dataset.sort;
                const productsGrid = document.getElementById('products-grid');
                const products = Array.from(productsGrid.children);
                
                products.sort((a, b) => {
                    const aValue = a.querySelector('.card-title').textContent;
                    const bValue = b.querySelector('.card-title').textContent;
                    
                    if (sortBy === 'price') {
                        const aPrice = parseFloat(a.querySelector('.card-text').textContent.replace('$', ''));
                        const bPrice = parseFloat(b.querySelector('.card-text').textContent.replace('$', ''));
                        return aPrice - bPrice;
                    }
                    
                    return aValue.localeCompare(bValue);
                });
                
                products.forEach(product => productsGrid.appendChild(product));
            });
        });

        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', async (e) => {
                const productId = e.target.closest('.add-to-cart').dataset.productId;
                try {
                    await ApiService.addToCart(productId);
                    Swal.fire({
                        icon: 'success',
                        title: 'Added to Cart',
                        text: 'Product has been added to your cart',
                        timer: 1500
                    });
                } catch (error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Failed to add product to cart'
                    });
                }
            });
        });

        document.querySelectorAll('.view-product').forEach(button => {
            button.addEventListener('click', (e) => {
                const productId = e.target.closest('.view-product').dataset.productId;
                navigateTo('product-details', { id: productId });
            });
        });

    } catch (error) {
        console.error('Error loading products page:', error);
        appContainer.innerHTML = `
            <div class="alert alert-danger" role="alert">
                Error loading products. Please try again later.
            </div>
        `;
    }
}

// Utility function for debouncing search input
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
} 