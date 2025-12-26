import { apiGet, apiPost } from "./api.js";

let allProducts = []; // 전체 상품 캐시
let currentWishes = []; // 현재 선택된 위시 키워드

// 상품 목록 로드
export async function loadAllProducts() {
    try {
        allProducts = await apiGet("/products"); // 전체 상품 가져오기
        renderProducts(allProducts);
    } catch (error) {
        console.error("상품 로드 실패:", error);
        renderProducts([]);
    }
}

// 검색
let searchTimeout;
export function searchProducts(keyword) {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        if (!keyword) {
            renderProducts(allProducts);
        } else {
            const filtered = allProducts.filter(p =>
                p.productName.toLowerCase().includes(keyword.toLowerCase())
            );
            renderProducts(filtered);
        }
    }, 300); // 300ms debounce
}

// 좋아요 토글
export async function toggleLike(productId) {
    try {
        await apiPost(`/products/${productId}/like`);
        // 좋아요 반영 후 캐시 업데이트
        const product = allProducts.find(p => p.id === productId);
        if (product) product.liked = !product.liked;
        renderProducts(allProducts);
    } catch (error) {
        console.error("좋아요 실패:", error);
    }
}

window.toggleLike = toggleLike;

// 위시 클릭 시 필터링
export function filterByWish(keyword) {
    const filtered = allProducts.filter(p =>
        p.productName.toLowerCase().includes(keyword.toLowerCase())
    );
    renderProducts(filtered);
}

// 상품 렌더링
export function renderProducts(products) {
    const productList = document.getElementById('product-list');

    if (!products || products.length === 0) {
        productList.innerHTML = `
          <div class="empty-state">
            <div class="empty-state-icon">📦</div>
            <p>상품이 없습니다</p>
          </div>
        `;
        return;
    }

    productList.innerHTML = products.map(product => {
        const discountRate = product.originalPrice > 0
            ? Math.round((1 - product.discountedPrice / product.originalPrice) * 100)
            : 0;

        return `
          <li class="product-card" onclick="window.open('${product.linkPath}', '_blank')">
            <button class="like-btn ${product.liked ? 'liked' : ''}" 
                    onclick="event.stopPropagation(); toggleLike('${product.id}')">
              ${product.liked ? '❤️' : '🤍'}
            </button>
            <img src="${product.imageUrl || 'https://via.placeholder.com/280x280?text=No+Image'}"
                 alt="${product.productName}"
                 class="product-image"
                 onerror="this.src='https://via.placeholder.com/280x280?text=No+Image'" />
            <div class="product-info">
              <div class="product-name">${product.productName}</div>
              <div class="price-container">
                <span class="original-price">${product.originalPrice.toLocaleString()}원</span>
                ${discountRate > 0 ? `<span class="discount-badge">${discountRate}%</span>` : ''}
              </div>
              <div class="discounted-price">${product.discountedPrice.toLocaleString()}원</div>
              <div class="product-meta">
                <span>${product.deliveryFeeType || '배송비 정보 없음'}</span>
                ${product.discountEndDate 
                  ? `<span>⏰ ${new Date(product.discountEndDate).toLocaleDateString()}</span>` 
                  : ''}
              </div>
            </div>
          </li>
        `;
    }).join('');
}