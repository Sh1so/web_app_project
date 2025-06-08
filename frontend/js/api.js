/**
 * API Service
 * Handles all communication with the backend REST API
 */

// Configuration - CHANGE THIS TO MATCH YOUR BACKEND
const API_CONFIG = {
    baseUrl: 'http://localhost:8080/api',
    endpoints: {
        products: '/products',
        categories: '/categories',
        cart: '/carts',
        orders: '/orders',
        users: '/users'
    }
};

// Mock data for development
const MOCK_DATA = {
    products: [
        { product_id: 1, short_name: 'Laptop Pro', price: 999.99, category_id: 1 },
        { product_id: 2, short_name: 'Smartphone X', price: 699.99, category_id: 2 },
        { product_id: 3, short_name: 'Wireless Earbuds', price: 149.99, category_id: 3 },
        { product_id: 4, short_name: 'Gaming Mouse', price: 79.99, category_id: 4 },
        { product_id: 5, short_name: '4K Monitor', price: 399.99, category_id: 1 },
        { product_id: 6, short_name: 'Mechanical Keyboard', price: 129.99, category_id: 4 }
    ],
    categories: [
        { category_id: 1, name: 'Computers', description: 'Laptops, desktops, and accessories' },
        { category_id: 2, name: 'Mobile Devices', description: 'Smartphones and tablets' },
        { category_id: 3, name: 'Audio', description: 'Headphones and speakers' },
        { category_id: 4, name: 'Gaming', description: 'Gaming peripherals and accessories' }
    ],
    cart: {
        items: [],
        total: 0
    }
};

/**
 * API Service object
 */
class ApiService {
    // Simulate API delay
    static async delay(ms = 500) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    static async request(endpoint, options = {}) {
        const url = `${API_CONFIG.baseUrl}${endpoint}`;
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json'
            }
        };

        try {
            const response = await fetch(url, { ...defaultOptions, ...options });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }

    // Products
    static async getAllProducts() {
        await this.delay();
        return MOCK_DATA.products;
    }

    static async getProductById(id) {
        await this.delay();
        return MOCK_DATA.products.find(p => p.product_id === parseInt(id));
    }

    // Categories
    static async getAllCategories() {
        await this.delay();
        return MOCK_DATA.categories;
    }

    // Cart
    static async getCart() {
        await this.delay();
        return MOCK_DATA.cart;
    }

    static async addToCart(productId, quantity = 1) {
        await this.delay();
        const product = MOCK_DATA.products.find(p => p.product_id === parseInt(productId));
        if (!product) throw new Error('Product not found');

        const existingItem = MOCK_DATA.cart.items.find(item => item.product_id === parseInt(productId));
        if (existingItem) {
            existingItem.quantity += quantity;
        } else {
            MOCK_DATA.cart.items.push({
                product_id: parseInt(productId),
                quantity: quantity,
                product: product
            });
        }

        this.updateCartTotal();
        return MOCK_DATA.cart;
    }

    static async removeFromCart(productId) {
        await this.delay();
        MOCK_DATA.cart.items = MOCK_DATA.cart.items.filter(item => item.product_id !== parseInt(productId));
        this.updateCartTotal();
        return MOCK_DATA.cart;
    }

    static updateCartTotal() {
        MOCK_DATA.cart.total = MOCK_DATA.cart.items.reduce((total, item) => {
            return total + (item.product.price * item.quantity);
        }, 0);
    }

    // Orders
    static async getOrders() {
        await this.delay();
        return [];
    }

    static async createOrder(orderData) {
        await this.delay();
        return { order_id: 1, ...orderData };
    }

    // User
    static async login(credentials) {
        await this.delay();
        if (credentials.email === 'admin@example.com' && credentials.password === 'admin') {
            return { token: 'mock-token', user: { id: 1, email: 'admin@example.com' } };
        }
        throw new Error('Invalid credentials');
    }

    static async register(userData) {
        await this.delay();
        return { id: 1, ...userData };
    }

    static async getProfile() {
        await this.delay();
        return { id: 1, email: 'admin@example.com', name: 'Admin User' };
    }
}