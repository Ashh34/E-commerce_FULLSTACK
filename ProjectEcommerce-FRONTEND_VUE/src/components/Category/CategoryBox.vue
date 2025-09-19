<template>
  <div v-if="category && category.id" class="category-card fade-in">
    <div class="image-container">
      <img
        class="category-img"
        :src="category?.imageUrl || '/default-category.png'"
        alt="Category Image"
      />
    </div>

    <div class="category-body">
      <router-link
        v-if="category?.id"
        :to="{ name: 'ListProducts', params: { id: category.id } }"
        class="category-title-link"
      >
        <h5 class="category-title">{{ category?.categoryName || 'No Name' }}</h5>
      </router-link>

      <p class="category-desc">
        {{ shortDescription }}...
      </p>

      <router-link
        v-if="category?.id"
        id="edit-category"
        :to="{ name: 'EditCategory', params: { id: category.id } }"
        v-show="$route.name === 'AdminCategory'"
        class="edit-link"
      >
        ✎ Edit
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "CategoryBox",
  props: {
    category: {
      type: Object,
      required: true
    }
  },
  computed: {
    shortDescription() {
      return this.category?.description
        ? this.category.description.substring(0, 65)
        : '';
    }
  }
};
</script>

<style scoped>
/* Card Container */
.category-card {
  background: #fff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  width: 100%;
  cursor: pointer;
}
.category-card:hover {
  transform: translateY(-6px) scale(1.02);
  box-shadow: 0 10px 20px rgba(0,0,0,0.12);
}

/* Image */
.image-container {
  position: relative;
  width: 100%;
  height: 180px;
  overflow: hidden;
}
.category-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.35s ease;
}
.category-card:hover .category-img {
  transform: scale(1.08);
}

/* Body */
.category-body {
  padding: 1rem 1.2rem;
}
.category-title-link {
  text-decoration: none;
}
.category-title {
  font-family: 'Poppins', sans-serif;
  font-weight: 600;
  font-size: 1.2rem;
  color: #4a148c;
  margin-bottom: 0.5rem;
  transition: color 0.3s ease;
}
.category-title-link:hover .category-title {
  color: #7b1fa2;
}
.category-desc {
  font-size: 0.9rem;
  color: #555;
  font-style: italic;
  line-height: 1.4;
  margin-bottom: 0.8rem;
}
.edit-link {
  font-size: 0.85rem;
  color: #a14f92;
  font-weight: 600;
  text-decoration: none;
  transition: color 0.25s ease;
}
.edit-link:hover {
  color: #7b1fa2;
}

/* Fade-in animation */
.fade-in {
  animation: fadeIn 0.8s ease-in-out;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>
