<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Your WishList</h4>
      </div>
    </div>

    <div class="row">
      <div v-for="product of products" :key="product.id"
        class="col-md-6 col-xl-4 col-12 pt-3 justify-content-around d-flex">

        <div class="card w-100">
          <ProductBox :product="product" />

          <div class="card-body text-center">
            <!-- ✅ Remove Button -->
            <button class="btn btn-danger btn-sm"
                    @click="removeFromWishlist(product.id)">
              Remove
            </button>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import ProductBox from '../../components/Product/ProductBox';

export default {
  name: 'WishlistPage',
  components: { ProductBox },
  props: ["baseURL"],
  data() {
    return {
      products: [],
      token: null
    };
  },
  methods: {
    fetchWishlist() {
      axios.get(`${this.baseURL}wishlist/${this.token}`)
        .then(res => this.products = res.data)
        .catch(err => console.log(err));
    },
    removeFromWishlist(productId) {
      axios.delete(`${this.baseURL}wishlist/remove/${productId}`, {
        params: { token: this.token }
      })
      .then(() => {
        // instantly update UI
        this.products = this.products.filter(p => p.id !== productId);
      })
      .catch(err => console.log(err));
    }
  },
  mounted() {
    this.token = localStorage.getItem('token');
    this.fetchWishlist();
  }
};
</script>

<style scoped>
h4 {
  font-family: 'Roboto', sans-serif;
  color: #484848;
  font-weight: 700;
}
</style>
