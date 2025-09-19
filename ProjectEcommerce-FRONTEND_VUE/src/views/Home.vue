<template>
  <div id="home">
    <!-- Hero Section -->
    <div id="background-div" class="page-holder bg-cover">
      <div class="overlay"></div>
      <div class="container py-5 text-center hero-content">
        <h1 class="mb-4 display-4 fw-bold animate__animated animate__fadeInDown">
          Welcome to <span class="highlight">BuyBloom – Bloom into Better Shopping with BuyBloom 🎉🌸</span>
        </h1>
        
        <a href="#start-shopping" class="btn btn-light btn-lg rounded-pill shadow-lg animate__animated animate__bounceIn">
          🛒 Start Shopping
        </a>
      </div>
    </div>

    <!-- Category Section -->
    <div id="start-shopping" class="container mt-5">
      <div class="row">
        <div class="col-12 text-center">
          <h2 class="section-title">✨ Top Categories</h2>
          <p class="section-subtitle">From essentials to trends – explore it all here 🌟</p>
        </div>
      </div>
      <div class="row">
        <div 
          v-for="index in category_size" 
          :key="index" 
          class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-center"
        >
          <CategoryBox :category="categories[index-1]" class="card-hover" />
        </div>
      </div>
    </div>

    <!-- Divider -->
    <hr class="my-5">

    <!-- Product Section -->
    <div class="container">
      <div class="row">
        <div class="col-12 text-center">
          <h2 class="section-title">🔥 Top Products</h2>
          <p class="section-subtitle">Discover our most-loved and trending picks 🎉</p>
        </div>
      </div>
      <div class="row">
        <div 
          v-for="index in product_size" 
          :key="index" 
          class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-center"
        >
          <ProductBox :product="products[index-1]" class="card-hover" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProductBox from "../components/Product/ProductBox";
import CategoryBox from "../components/Category/CategoryBox";

export default {
  name: 'HomePage',
  components : { ProductBox, CategoryBox },
  props : ["baseURL", "products", "categories"],
  data(){
    return{
      category_size: 0,
      product_size: 0
    }
  },
  mounted(){
    this.category_size = Math.min(6, this.categories.length);
    this.product_size = Math.min(8, this.products.length);
  }
}
</script>

<style>
/* General Layout */
.page-holder {
  min-height: 100vh;
  position: relative;
}

.bg-cover {
  background-size: cover !important;
  position: relative;
}

#background-div {
  background: url(../assets/bbg.jpg) no-repeat center center;
  background-size: cover;
  aspect-ratio: 16 / 9;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.overlay {
  position: absolute;
  top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.5);
}

.hero-content {
  position: relative;
  z-index: 2;
  color: #fff;
}

.highlight {
  color: #ffd700;
}

.section-title {
  font-family: 'Josefin Sans', sans-serif;
  font-weight: bold;
  font-size: 2rem;
}

.section-subtitle {
  font-size: 1rem;
  opacity: 0.7;
}

/* Hover Effects */
.card-hover {
  transition: all 0.3s ease-in-out;
}

.card-hover:hover {
  transform: translateY(-8px) scale(1.03);
  box-shadow: 0 10px 20px rgba(0,0,0,0.2);
}

/* Animations (using animate.css or custom) */
@import "https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css";
</style>
