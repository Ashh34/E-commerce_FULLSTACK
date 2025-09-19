<template>
  <div class="div_class">
    <h3>You will be redirected to payment page</h3>

    <div class="alert alert-primary" role="alert">
      While making payment use card number <b>4242 4242 4242 4242</b> with any
      future expiry date and random 3-digit CVV.
    </div>

    <button
      class="checkout_button"
      id="proceed-to-checkout"
      @click="goToCheckout"
      :disabled="loading"
    >
      {{ loading ? "Processing..." : "Make Payment" }}
    </button>
  </div>
</template>

<script>
import axios from "axios";
import { loadStripe } from "@stripe/stripe-js";

export default {
  name: "CheckoutPage",
  props: ["baseURL"],

  data() {
    return {
      // Keep your current env var scheme; if you use Vite, switch to import.meta.env.VITE_*
      stripeAPIToken: process.env.VUE_APP_STRIPETOKEN || "",
      stripe: null,
      token: null,
      sessionId: null,
      checkoutBodyArray: [],
      loading: false,
    };
  },

  methods: {
    async getAllItems() {
      try {
        if (!this.baseURL) {
          console.error("baseURL prop is missing.");
          return;
        }
        const response = await axios.get(`${this.baseURL}cart/?token=${this.token}`);
        if (response.status === 200 && Array.isArray(response.data.cartItems)) {
          this.checkoutBodyArray = response.data.cartItems.map((item) => ({
            productName: item.product.name,
            quantity: item.quantity,
            price: item.product.price,
          }));
        }
      } catch (err) {
        console.error("Error fetching cart items:", err);
      }
    },

    async goToCheckout() {
      if (!this.stripe) {
        alert("Stripe failed to initialize. Check your publishable key in .env");
        return;
      }
      if (!this.checkoutBodyArray.length) {
        alert("Your cart is empty!");
        return;
      }

      this.loading = true;
      try {
        const response = await axios.post(
          `${this.baseURL}order/create-checkout-session`,
          this.checkoutBodyArray,
          { headers: { "Content-Type": "application/json" } }
        );

        if (response.data && response.data.sessionId) {
          this.sessionId = response.data.sessionId;
          localStorage.setItem("sessionId", this.sessionId);

          const result = await this.stripe.redirectToCheckout({
            sessionId: this.sessionId,
          });

          if (result.error) {
            console.error("Stripe Checkout Error:", result.error.message);
            alert(result.error.message || "Unable to redirect to Stripe.");
          }
        } else {
          alert("Checkout failed to initialize. Please try again.");
        }
      } catch (err) {
        console.error("Checkout error:", err);
        const msg = err?.response?.data?.message || err.message || "Checkout failed";
        alert(msg);
      } finally {
        this.loading = false;
      }
    },
  },

  async mounted() {
    this.token = localStorage.getItem("token");

    try {
      this.stripe = await loadStripe(this.stripeAPIToken);
    } catch (e) {
      console.error("Stripe JS failed to load:", e);
    }

    if (!this.stripe) {
      console.error("Stripe failed to initialize. Check your publishable key.");
    }

    await this.getAllItems();
  },
};
</script>

<style>
.alert {
  width: 50%;
}

.div_class {
  margin-top: 5%;
  margin-left: 30%;
}

.checkout_button {
  background-color: #5d3dec;
  border: none;
  color: white;
  margin-left: 15%;
  padding: 15px 30px;
  text-align: center;
  display: inline-block;
  font-size: 15px;
  font-weight: bold;
  border-radius: 15px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.checkout_button:hover {
  background-color: #4b2ed1;
}

.checkout_button:disabled {
  background-color: #9b86f7;
  cursor: not-allowed;
}
</style>
