<template>
  <div class="category-page">
    <!-- Header Row -->
    <div class="row align-items-center mb-4">
      <div class="col-12 col-md-8">
        <h4 class="page-heading">Our Categories</h4>
      </div>
      <div class="col-12 col-md-4 text-md-right mt-3 mt-md-0">
        <router-link
          id="add-category"
          :to="{ name: 'AddCategory' }"
          v-show="$route.name === 'AdminCategory'"
        >
          <button class="btn-add-category">+ Add New Category</button>
        </router-link>
      </div>
    </div>

    <!-- Categories Grid -->
    <div class="row">
      <div
        v-for="category of categories"
        :key="category.id"
        class="col-md-6 col-xl-4 col-12 pt-3 d-flex justify-content-center category-card-wrapper"
      >
        <CategoryBox :category="category" />
      </div>
    </div>
  </div>
</template>

<script>
import CategoryBox from '../../components/Category/CategoryBox';
export default {
  name: 'CategoryPage',
  components : {CategoryBox},
  props : [ "baseURL" , "categories" ],
  mounted(){
    if (this.$route.name=='AdminCategory' && !localStorage.getItem('token')) {
      this.$router.push({name : 'Signin'});
    }
  }
}
</script>

<style scoped>
/* Page Layout */
.category-page {
  background: linear-gradient(135deg, #fafafa, #f3e5f5);
  min-height: 100vh;
  padding: 3rem 2rem;
}

/* Heading */
.page-heading {
  font-family: 'Poppins', sans-serif;
  color: #4a148c;
  font-weight: 600;
  font-size: 1.6rem;
  letter-spacing: 0.5px;
  border-left: 4px solid #7b1fa2;
  padding-left: 12px;
  margin-bottom: 0;
}

/* Add Button */
.btn-add-category {
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
.btn-add-category:hover {
  background: linear-gradient(90deg, #a14f92, #8d3f7f);
  transform: translateY(-3px) scale(1.05);
  box-shadow: 0 6px 18px rgba(141, 63, 127, 0.55);
}
/* Shine effect */
.btn-add-category::after {
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

/* Card wrapper hover lift */
.category-card-wrapper {
  transition: transform 0.25s ease, box-shadow 0.25s ease;
}
.category-card-wrapper:hover {
  transform: translateY(-4px);
}
</style>
