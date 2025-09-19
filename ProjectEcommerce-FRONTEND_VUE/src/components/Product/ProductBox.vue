<template>
  <div class="card product-card">
    <!-- Product Image -->
    <div class="image-wrapper">
      <img
        class="card-img-top"
        :src="product.imageURL"
        alt="Product Image"
      />
    </div>

    <!-- Card Body -->
    <div class="card-body d-flex flex-column">
      <router-link :to="{ name: 'ShowDetails', params: { id: product.id } }">
        <h5 class="card-title">{{ product.name }}</h5>
      </router-link>

      <p class="price"><sup>$</sup>{{ product.price }}</p>

      <p class="description">
        {{ product.description.substring(0, 65) }}...
      </p>

      <router-link
        id="edit-product"
        :to="{ name: 'EditProduct', params: { id: product.id } }"
        v-if="$route.name === 'AdminProduct'"
      >
        ✏️ Edit
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProductBox",
  props: ["product"]
};
</script>

<style scoped>
/* Standard Card */
.product-card {
  width: 100%;
  max-width: 320px;       /* Standard width */
  min-height: 400px;      /* Standard height */
  border: none;
  border-radius: 14px;
  overflow: hidden;
  margin: 12px;           /* space between cards */
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  position: relative;
  z-index: 0;
  display: flex;
  flex-direction: column;
}
.product-card:hover {
  transform: translateY(-8px) scale(1.02);
  box-shadow: 0 10px 24px rgba(0,0,0,0.15);
  z-index: 5; /* stays above neighbors */
}

/* Image */
.image-wrapper {
  height: 200px;
  overflow: hidden;
}
.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}
.product-card:hover img {
  transform: scale(1.08);
}

/* Title */
.card-title {
  color: #6a0572;
  font-size: 1.15rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
  transition: color 0.3s ease;
}
.card-title:hover {
  color: #b565a7;
}

/* Price */
.price {
  color: #e63946;
  font-weight: 700;
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

/* Description */
.description {
  color: #555;
  font-size: 0.85rem;
  line-height: 1.3;
  flex-grow: 1; /* pushes edit link down */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Edit Link */
#edit-product {
  font-size: 0.85rem;
  color: #6a0572;
  font-weight: 500;
  align-self: flex-end;
}
#edit-product:hover {
  color: #b565a7;
  text-decoration: underline;
}
</style>
