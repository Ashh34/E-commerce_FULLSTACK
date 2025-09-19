<template>
  <Navbar
    :cartCount="cartCount"
    @resetCartCount="resetCartCount"
    v-if="!['Signup', 'Signin'].includes($route.name)"
  />
  
  <div style="min-height: 60vh">
    <router-view
      v-if="products && categories"
      :baseURL="baseURL"
      :products="products"
      :categories="categories"
      @fetchData="fetchData"
    />
  </div>

  <Footer v-if="!['Signup', 'Signin'].includes($route.name)" />
</template>

<script>
import axios from "axios";
import Navbar from './components/Navbar.vue';
import Footer from './components/Footer.vue';

export default {
  components: { Navbar, Footer },

  data() {
    return {
      baseURL: "http://localhost:8081/",
      products: null,
      categories: null,
      cartCount: 0,
      token: null,
    };
  },

  methods: {
    async fetchData() {
      try {
        // Fetch products and categories in parallel
        const [productsRes, categoriesRes] = await Promise.all([
          axios.get(this.baseURL + 'product/'),
          axios.get(this.baseURL + 'api/categories/list')
        ]);

        this.products = productsRes.data;
        this.categories = categoriesRes.data;

        // Fetch cart items if user is logged in
        if (this.token) {
          const cartRes = await axios.get(`${this.baseURL}cart/?token=${this.token}`);
          this.cartCount = Object.keys(cartRes.data?.cartItems || {}).length;
        }

      } catch (err) {
        console.log("Error fetching data:", err);
      }
    },

    resetCartCount() {
      this.cartCount = 0;
    }
  },

  mounted() {
    // Get token from localStorage
    this.token = localStorage.getItem('token');
    this.fetchData();
  }
};
</script>

<style>
html {
  overflow-y: scroll;
}
</style>
