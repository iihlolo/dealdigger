import { apiGet, apiPost } from "./api.js";

export async function loadAllProducts() {
    const products = await apiGet("/products");
    renderProducts(products);
}

export async function searchProducts(keyword) {
    const products = await apiGet(`/products/search?keyword=${encodeURIComponent(keyword)}`);
    renderProducts(products);
}

export async function toggleLike(productId) {
    await apiPost(`/products/${productId}/like`);
    await loadAllProducts();
}

function renderProducts(products) {
    const ul = document.getElementById("product-list");
    ul.innerHTML = products.map(p => `
        <li>
            <a href="${p.linkPath}" target="_blank" rel="noopener noreferrer">
                <strong>${p.productName}</strong>
            </a>
            <button data-id="${p.id}" class="like-btn">${p.liked ? "♥" : "♡"}</button>
        </li>
    `).join("");

    document.querySelectorAll(".like-btn").forEach(btn => {
        btn.onclick = () => toggleLike(btn.dataset.id);
    });
}