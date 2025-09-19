<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm py-2">
    <!-- Logo -->
    <router-link class="navbar-brand d-flex align-items-center" :to="{ name: 'Home' }">
      <img id="logo" src="../assets/bbb.png" alt="Logo" />
    </router-link>

    <!-- Burger Button -->
    <button
      class="navbar-toggler"
      type="button"
      data-bs-toggle="collapse"
      data-bs-target="#navbarContent"
      aria-controls="navbarContent"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Navbar Content -->
    <div class="collapse navbar-collapse" id="navbarContent">
      <!-- Search Bar -->
      <form class="d-flex mx-auto search-bar">
        <input
          class="form-control"
          type="search"
          placeholder="Search items..."
          aria-label="Search"
        />
        <button class="btn btn-warning ms-2" type="submit">
          <i class="bi bi-search"></i>
        </button>
      </form>

      <!-- Menu -->
      <ul class="navbar-nav ms-auto align-items-lg-center">
        <!-- Browse -->
        <li class="nav-item dropdown">
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="browseMenu"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Browse
          </a>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="browseMenu">
            <router-link class="dropdown-item" :to="{ name: 'Home' }">Home</router-link>
            <router-link class="dropdown-item" :to="{ name: 'Product' }">Product</router-link>
            <router-link class="dropdown-item" :to="{ name: 'Category' }">Category</router-link>
          </ul>
        </li>

        <!-- Accounts -->
        <li class="nav-item dropdown ms-lg-3">
          <a
            class="nav-link dropdown-toggle"
            href="#"
            id="accountMenu"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Account
          </a>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="accountMenu">
            <router-link class="dropdown-item" v-if="!token" :to="{ name: 'Signin' }">Wishlist</router-link>
            <router-link class="dropdown-item" v-else :to="{ name: 'Wishlist' }">Wishlist</router-link>
            <router-link class="dropdown-item" :to="{ name: 'Admin' }">Admin</router-link>
            <router-link class="dropdown-item" v-if="!token" :to="{ name: 'Signin' }">Log In</router-link>
            <router-link class="dropdown-item" v-if="!token" :to="{ name: 'Signup' }">Sign Up</router-link>
            <a class="dropdown-item text-danger" v-if="token" href="#" @click="signout">Sign Out</a>
          </ul>
        </li>

        <!-- Orders -->
        <li class="nav-item ms-lg-3">
          <router-link class="nav-link" :to="{ name: 'Order' }">Orders</router-link>
        </li>

        <!-- Cart -->
        <li class="nav-item position-relative ms-lg-3">
          <router-link class="nav-link" :to="{ name: 'Cart' }">
            <i class="fa fa-shopping-cart fa-lg"></i>
            <span id="nav-cart-count">{{ cartCount }}</span>
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import swal from "sweetalert";

export default {
  name: "NavbarPage",
  props: ["cartCount"],
  data() {
    return { token: null };
  },
  methods: {
    signout() {
      localStorage.removeItem("token");
      this.token = null;
      this.$emit("resetCartCount");
      this.$router.push({ name: "Home" });
      swal({
        text: "Logged you out. Visit Again",
        icon: "success",
        closeOnClickOutside: false,
      });
    },
  },
  mounted() {
    this.token = localStorage.getItem("token");
  },
};
</script>

<style scoped>
/* Logo */
#logo {
  width: 120px;
  transition: transform 0.3s ease;
}
#logo:hover {
  transform: scale(1.05);
}

/* Soft pink–purple gradient navbar */
.navbar {
  background: linear-gradient(90deg, #F3EDE3, #F3EDE3);
  border-bottom: 1px solid #e8dff5;
}

/* Links */
.nav-link {
  color: #4a3f55 !important;
  font-weight: 500;
  transition: color 0.3s ease, transform 0.2s ease;
}
.nav-link:hover {
  color: #7d5275 !important; /* Soft purple accent */
  transform: translateY(-1px);
}

/* Dropdown menu */
.dropdown-menu {
  background-color: #ffffff;
  border-radius: 8px;
  border: 1px solid #e4d8f9;
  box-shadow: 0 4px 10px rgba(181, 101, 167, 0.08);
}
.dropdown-item {
  color: #4a3f55;
  transition: background-color 0.3s ease, color 0.3s ease;
}
.dropdown-item:hover {
  background-color: #fce3ec;
  color: #b565a7;
}

/* Search bar */
.search-bar input {
  min-width: 250px;
  border-radius: 25px;
  padding: 0.5rem 1rem;
  border: 2px solid #e8dff5;
  background-color: #fff;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}
.search-bar input:focus {
  border-color: #b565a7;
  outline: none;
  box-shadow: 0 0 6px rgba(181, 101, 167, 0.3);
}
.search-bar button {
  border-radius: 25px;
  background-color: #c6bac4;
  color: #fff;
  font-weight: bold;
  transition: background-color 0.3s ease;
}
.search-bar button:hover {
  background-color: #a14f92;
}

/* Cart badge */
#nav-cart-count {
  background-color: #b565a7;
  color: white;
  border-radius: 50%;
  font-size: 0.75rem;
  padding: 3px 7px;
  position: absolute;
  top: 4px;
  right: -8px;
  font-weight: bold;
  box-shadow: 0 0 6px rgba(181, 101, 167, 0.4);
}

/* Smooth transitions */
.navbar, .dropdown-menu, #logo, .nav-link, .dropdown-item, .search-bar input, .search-bar button {
  transition: all 0.3s ease;
}
</style>




