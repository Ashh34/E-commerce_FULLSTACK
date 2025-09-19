<template>
  <div class="container text-center mt-5">
    <h2>Payment cancelled ❌</h2>
    <p>You can try again from the cart.</p>
    <button class="btn btn-secondary mt-3" @click="$router.push({ name: 'Cart' })">
      Back to Cart
    </button>
  </div>
</template>

<script>
export default { name: "PaymentFailed" };
</script>
