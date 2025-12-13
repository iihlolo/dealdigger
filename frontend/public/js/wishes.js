import { apiGet, apiPost, apiDelete } from "./api.js";

export async function loadWishes() {
  const wishes = await apiGet("/wishes");
  const ul = document.getElementById("wish-list");
  ul.innerHTML = wishes.map(w => `
    <li>
      ${w.keyword}
      <button data-id="${w.id}">삭제</button>
    </li>
  `).join("");

  ul.querySelectorAll("button").forEach(btn => {
    btn.onclick = async () => {
      await apiDelete(`/wishes/${btn.dataset.id}`);
      loadWishes();
    };
  });
}

export async function createWish(keyword) {
  await apiPost("/wishes", { keyword });
  await loadWishes();
}