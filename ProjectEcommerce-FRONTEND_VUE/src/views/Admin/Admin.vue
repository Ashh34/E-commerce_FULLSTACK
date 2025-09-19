<template>
  <div class="adminPanel container">
    <router-link :to="{ name: 'AdminProduct' }">
      <button type="button" class="btn-menu">Admin Products</button>
    </router-link>

    <router-link :to="{ name: 'AdminCategory' }">
      <button type="button" class="btn-menu">Admin Categories</button>
    </router-link>

    <router-link :to="{ name: 'Gallery' }">
      <button type="button" class="btn-menu">Admin Gallery</button>
    </router-link>
  </div>
</template>



<script>
export default {
  name: "AdminPanel",
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
  }
};
</script>


<style scoped>
.adminPanel {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 3rem 1rem;
  background: linear-gradient(135deg, #fce3ec, #e4d8f9);
  min-height: 100vh;
}

/* Menu Buttons */
.btn-menu {
  font-size: 1.4rem;
  font-weight: 500;
  background-color: #b565a7;
  color: white;
  padding: 15px 40px;
  margin: 20px 0;
  border: none;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(181, 101, 167, 0.2);
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.btn-menu:hover {
  background-color: #a14f92;
  transform: translateY(-3px);
}
</style>
