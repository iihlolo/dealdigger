import { loadAllProducts, searchProducts, renderProducts } from "./products.js";
import { loadWishes, createWish, apiGet } from "./wishes.js";

document.addEventListener("DOMContentLoaded", async () => {
  const searchInput = document.getElementById("search");
  const wishForm = document.getElementById("wish-form");
  const wishInput = document.getElementById("wish-input");
  const wishList = document.getElementById("wish-list");

  // 상품 목록과 위시 목록 초기 로드
  await loadAllProducts();
  await refreshWishes();

  // 검색창 이벤트
  searchInput.addEventListener("input", async (e) => {
    const keyword = e.target.value.trim();
    const wishes = await apiGet("/wishes");
    if (!keyword) {
      if (wishes.length > 0) {
        await filterProductsByWishes(wishes.map(w => w.keyword));
      } else {
        await loadAllProducts();
      }
    } else {
      await searchProducts(keyword);
    }
  });

  // 위시 추가 이벤트
  wishForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const keyword = wishInput.value.trim();
    if (keyword) {
      await createWish(keyword);
      wishInput.value = "";
      await refreshWishes();
    }
  });

  // 위시 클릭 시 필터링
  async function attachWishClick() {
    wishList.querySelectorAll("li span").forEach(span => {
      span.onclick = async () => {
        const wishes = [span.textContent];
        await filterProductsByWishes(wishes);
        searchInput.value = ""; // 검색창 초기화
      };
    });
  }

  // 위시 목록 새로고침
  async function refreshWishes() {
    await loadWishes();
    await attachWishClick();
  }

  // 위시 기반 상품 필터링
  async function filterProductsByWishes(wishKeywords) {
    const allProducts = await apiGet("/products");
    const filtered = allProducts.filter(product =>
      wishKeywords.some(wish => product.productName.includes(wish))
    );
    renderProducts(filtered);
  }
});