<template>
  <div class="product-page">
    <!-- Header Row -->
    <div class="row align-items-center mb-4">
      <div class="col-12 col-md-8 d-flex align-items-center">
        <h4 class="mb-0">Our Products</h4>
        <!-- Search Bar -->
        <input
          v-model="searchQuery"
          class="search-input ml-3"
          type="text"
          placeholder="🔍 Search products..."
        />
      </div>

      <!-- ✅ Button only visible on /admin/product -->
      <div class="col-12 col-md-4 text-md-right mt-3 mt-md-0">
        <router-link
          id="add-product"
          :to="{ name: 'AddProduct' }"
          v-show="$route.name === 'AdminProduct'"
        >
          <button class="btn btn-add-product">+ Add New Product</button>
        </router-link>
      </div>
    </div>

    <!-- Product List -->
    <div class="row fade-in">
      <div
        v-for="product of filteredProducts"
        :key="product.id"
        class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-center product-card-wrapper"
      >
        <ProductBox :product="product" />
      </div>
    </div>
  </div>
</template>

<script>
import ProductBox from "../../components/Product/ProductBox";

export default {
  name: "ProductPage",
  components: { ProductBox },
  props: ["baseURL", "products"],
  data() {
    return {
      searchQuery: ""
    };
  },
  computed: {
    filteredProducts() {
      return this.products.filter((p) =>
        p.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  mounted() {
    // if admin route but no token -> redirect to signin
    if (this.$route.name === "AdminProduct" && !localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
  }
};
</script>

<style scoped>
/* Page Layout */
.product-page {
  background: #fafafa;
  min-height: 100vh;
  padding: 3rem 2rem;
}

/* Heading Style */
h4 {
  font-family: 'Poppins', sans-serif;
  color: #4a148c;
  font-weight: 600;
  font-size: 1.5rem;
  letter-spacing: 0.5px;
  border-left: 4px solid #7b1fa2;
  padding-left: 10px;
  margin-bottom: 0;
}

/* Search Input */
.search-input {
  border: 1px solid #ddd;
  border-radius: 20px;
  padding: 6px 14px;
  font-size: 0.9rem;
  transition: all 0.2s ease;
  background-color: #fff;
}
.search-input:focus {
  outline: none;
  border-color: #7b1fa2;
  box-shadow: 0 0 0 2px rgba(123, 31, 162, 0.15);
}

/* Product Card Hover */
.row {
  overflow: visible !important;
}
.product-card-wrapper {
  transition: transform 0.25s ease, box-shadow 0.25s ease;
  position: relative;
}
.product-card-wrapper:hover {
  transform: translateY(-6px) scale(1.01);
  box-shadow: 0 6px 16px rgba(0,0,0,0.1);
  z-index: 5;
}

/* Add Product Button with Glow */
.btn-add-product {
  background: linear-gradient(90deg, #b565a7, #a14f92);
  color: #fff;
  font-weight: 600;
  border-radius: 50px;
  padding: 10px 24px;
  border: none;
  cursor: pointer;
  box-shadow: 0 4px 15px rgba(181, 101, 167, 0.4);
  transition: transform 0.25s ease, box-shadow 0.25s ease, background 0.4s ease;
  position: relative;
  overflow: hidden;
}

.btn-add-product:hover {
  background: linear-gradient(90deg, #a14f92, #8d3f7f);
  transform: translateY(-3px) scale(1.05);
  box-shadow: 0 6px 18px rgba(141, 63, 127, 0.55);
}

/* Shine effect */
.btn-add-product::after {
  content: "";
  position: absolute;
  top: 0;
  left: -75%;
  width: 50%;
  height: 100%;
  background: linear-gradient(
    120deg,
    rgba(255, 255, 255, 0) 0%,
    rgba(255, 255, 255, 0.4) 50%,
    rgba(255, 255, 255, 0) 100%
  );
  transform: skewX(-25deg);
  animation: shine 3s infinite;
}

@keyframes shine {
  0%   { left: -75%; }
  100% { left: 125%; }
}


/* Fade-in animation for products */
.fade-in {
  animation: fadeIn 0.8s ease-in-out;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(15px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
