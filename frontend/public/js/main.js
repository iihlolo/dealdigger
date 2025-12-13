import { loadAllProducts, searchProducts } from "./products.js";
import { loadWishes, createWish } from "./wishes.js";

document.addEventListener("DOMContentLoaded", () => {
  loadAllProducts();
  loadWishes();

  document.getElementById("search").addEventListener("input", e => {
    const keyword = e.target.value.trim();
    if (!keyword) loadAllProducts();
    else searchProducts(keyword);
  });

  document.getElementById("wish-form").addEventListener("submit", e => {
    e.preventDefault();
    const input = document.getElementById("wish-input");
    if (input.value) {
      createWish(input.value);
      input.value = "";
    }
  });
});