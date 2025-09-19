<template>
  <div class="container text-center mt-5">
    <h2>Payment successful 🎉</h2>
    <p v-if="placing">Placing your order and clearing your cart...</p>
    <p v-else-if="error" class="text-danger">Failed to finalize order: {{ error }}</p>
    <p v-else>Your order is placed. Redirecting to your cart…</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "PaymentSuccess",
  props: ["baseURL"],

  data() {
    return {
      placing: true,
      error: null,
    };
  },

  methods: {
    async finalizeOrder() {
      try {
        const token = localStorage.getItem("token");
        // Get the session id from Stripe's redirect param
        const sessionId =
          this.$route.query.session_id || localStorage.getItem("sessionId");

        if (!token || !sessionId) {
          this.error = "Missing token or session id.";
          this.placing = false;
          return;
        }

        // Call backend to place order (this clears the cart in OrderService.placeOrder)
        const url = `${this.baseURL}order/add?token=${encodeURIComponent(
          token
        )}&sessionId=${encodeURIComponent(sessionId)}`;

        await axios.post(url);

        // optional cleanup
        localStorage.removeItem("sessionId");

        this.placing = false;

        // redirect to cart so user sees empty cart now
        this.$router.replace({ name: "Cart" });
      } catch (e) {
        console.error(e);
        this.error =
          e?.response?.data?.message ||
          e?.message ||
          "Unexpected error while placing order.";
        this.placing = false;
      }
    },
  },

  mounted() {
    this.finalizeOrder();
  },
};
</script>
