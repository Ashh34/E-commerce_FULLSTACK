<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h3 class="pt-3">Shopping Cart</h3>
      </div>
    </div>

    <!-- Loop over cart items -->
    <div v-for="cartItem in cartItems" :key="cartItem.id" class="row mt-2 pt-3 justify-content-around">
      <div class="col-2"></div>

      <!-- Product Image -->
      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <router-link :to="{ name: 'ShowDetails', params: { id: cartItem.product.id } }">
          <img :src="cartItem.product.imageURL" class="w-100 card-img-top embed-responsive-item" />
        </router-link>
      </div>

      <!-- Product Info -->
      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
            <router-link :to="{ name: 'ShowDetails', params: { id: cartItem.product.id } }">
              {{ cartItem.product.name }}
            </router-link>
          </h6>

          <p class="mb-0 font-weight-bold">
            $ {{ cartItem.product.price }} per unit
          </p>

          <p class="mb-0">
            Quantity:
            <input type="number" min="1" v-model.number="cartItem.quantity" @change="updateQuantity(cartItem)"
              class="quantity-input" />
          </p>

          <p class="mb-0">
            Total: <span class="font-weight-bold">$ {{ cartItem.product.price * cartItem.quantity }}</span>
          </p>

          <br />
          <button class="btn-remove" @click.prevent="deleteItem(cartItem.id)">
            Remove From Cart
          </button>

        </div>
      </div>

      <div class="col-2"></div>
      <div class="col-12">
        <hr />
      </div>
    </div>

    <!-- Total Cost -->
    <div class="total-cost pt-2 text-right">
      <h5>Total: $ {{ totalcost.toFixed(2) }}</h5>
      <button :disabled="cartItems.length === 0" class="btn btn-primary confirm" @click="checkout">
        Confirm Order
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CartPage",
  props: ["baseURL"],
  data() {
    return {
      cartItems: [],
      token: null,
      totalcost: 0
    };
  },
  mounted() {
    this.token = localStorage.getItem("token");
    this.listCartItems();
  },
  methods: {
    // Fetch cart items from backend
    listCartItems() {
      axios
        .get(`${this.baseURL}cart/?token=${this.token}`)
        .then((response) => {
          const result = response.data;
          this.cartItems = result.cartItems;
          this.totalcost = result.totalCost;
        })
        .catch((error) => console.error(error));
    },

    // Update quantity for a single cart item
    updateQuantity(cartItem) {
      axios
        .put(
          `${this.baseURL}cart/update/${cartItem.id}?token=${this.token}`,
          {
            id: cartItem.id,
            productId: cartItem.product.id,
            quantity: cartItem.quantity
          },
          {
            headers: { "Content-Type": "application/json" }
          }
        )
        .then(() => this.listCartItems())
        .catch((error) => console.error(error));
    },

    // Delete a cart item
    deleteItem(itemId) {
      axios
        .delete(`${this.baseURL}cart/delete?cartItemId=${itemId}&token=${this.token}`)
        .then(() => this.listCartItems())
        .catch((error) => console.error(error));
    },

    // Proceed to checkout page
    checkout() {
      this.$router.push({ name: "Checkout" });
    }
  }
};
</script>

<style scoped>
/* General Typography */
h3,
h5 {
  font-family: 'Roboto', sans-serif;
  color: #333;
  font-weight: 700;
}

/* Cart item row */
.row {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.row:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  border-radius: 12px;
  background-color: #fff;
  padding: 10px 0;
}

/* Product Card Image */
.embed-responsive {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
}

.embed-responsive:hover {
  transform: scale(1.03);
}

/* Product Title */
.card-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #4a148c;
  margin-bottom: 0.3rem;
  transition: color 0.3s ease;
}

.card-title:hover {
  color: #b565a7;
  text-decoration: underline;
}

/* Price */
.mb-0.font-weight-bold {
  color: #e63946;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

/* Quantity Input */
.quantity-input {
  width: 60px;
  padding: 4px 8px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
  transition: all 0.2s ease;
}

.quantity-input:focus {
  outline: none;
  border-color: #4a148c;
  box-shadow: 0 0 6px rgba(74, 20, 140, 0.3);
}

/* Remove link */
a {
  color: #d00000;
  font-weight: 500;
  transition: color 0.2s ease;
}

a:hover {
  color: #a30000;
  text-decoration: underline;
}

/* Total Cost Section */
.total-cost {
  background: #f3e5f5;
  border-radius: 12px;
  padding: 15px 20px;
  margin-top: 20px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.total-cost:hover {
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

/* Confirm Button */
.confirm {
  font-weight: bold;
  font-size: 1rem;
  padding: 10px 24px;
  border-radius: 30px;
  border: none;
  background: linear-gradient(90deg, #7b1fa2, #b565a7);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.confirm:hover {
  background: linear-gradient(90deg, #b565a7, #7b1fa2);
  transform: scale(1.05);
  box-shadow: 0 6px 20px rgba(181, 101, 167, 0.4);
}

.confirm:disabled {
  cursor: not-allowed;
  background: #ccc;
  color: #666;
  transform: none;
  box-shadow: none;
}

/* Horizontal Rule Styling */
hr {
  border: 0;
  height: 1px;
  background: #ddd;
  margin: 10px 0;
}

/* Fade-in animation for cart items */
.row {
  opacity: 0;
  animation: fadeIn 0.6s forwards;
}

@keyframes fadeIn {
  to {
    opacity: 1;
  }
}

/* Remove From Cart Button */
.btn-remove {
  background-color: #e63946;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 6px 14px;
  font-size: 0.85rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 8px;
}

.btn-remove:hover {
  background-color: #d00000;
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(208, 0, 0, 0.4);
}

</style>
