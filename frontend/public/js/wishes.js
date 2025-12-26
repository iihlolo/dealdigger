import { apiGet, apiPost, apiDelete } from "./api.js";
import { filterByWish } from "./products.js";

let currentWishes = [];

// 위시 목록 로드
export async function loadWishes() {
  try {
    currentWishes = await apiGet("/wishes");
    const ul = document.getElementById("wish-list");
    ul.innerHTML = currentWishes.map(w => `
      <li>
        <span class="wish-keyword" data-keyword="${w.keyword}">${w.keyword}</span>
        <button class="delete-btn" data-id="${w.id}">×</button>
      </li>
    `).join("");

    // 삭제 버튼
    ul.querySelectorAll(".delete-btn").forEach(btn => {
      btn.onclick = async (e) => {
        e.stopPropagation();
        await apiDelete(`/wishes/${btn.dataset.id}`);
        await loadWishes();
      };
    });

    // 키워드 클릭 → 상품 필터링
    ul.querySelectorAll(".wish-keyword").forEach(span => {
      span.onclick = () => {
        const keyword = span.dataset.keyword;
        filterByWish(keyword);
      };
    });

  } catch (error) {
    console.error("위시 로드 실패:", error);
  }
}

// 위시 추가
export async function createWish(keyword) {
  try {
    await apiPost("/wishes", { keyword });
    await loadWishes();
  } catch (error) {
    console.error("위시 추가 실패:", error);
  }
}