<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Order Id: {{ orderID }}</h4>
      </div>
    </div>

    <div
      v-for="(orderItem, index) in orderItems"
      :key="index"
      class="row mt-2 pt-3 justify-content-around"
    >
      <div class="col-1"></div>

      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <img :src="orderItem.product.imageURL" class="w-100 card-img-top embed-responsive-item" />
        <hr />
      </div>

      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">{{ orderItem.product.name }}</h6>
          <p id="item-price" class="mb-0 font-weight-bold">
            ${{ orderItem.product.price }} per unit
          </p>
          <p id="item-quantity" class="mb-0">Quantity : {{ orderItem.quantity }}</p>
          <p id="item-total-price" class="mb-0">
            Total Price :
            <span class="font-weight-bold">
              ${{ (orderItem.price || orderItem.product.price) * orderItem.quantity }}
            </span>
          </p>
        </div>
      </div>

      <div class="col-1"></div>
    </div>

    <div class="total-cost pt-2 text-right" v-if="order && order.totalPrice != null">
      <h5>Total Cost : $ {{ order.totalPrice }}</h5>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderItems",
  props: ["baseURL"],

  data() {
    return {
      orderItems: [],
      order: {},
      token: null,
      orderID: null,
    };
  },

  methods: {
    async getOrder() {
      try {
        const res = await axios.get(`${this.baseURL}order/${this.orderID}?token=${this.token}`);
        if (res.status === 200) {
          this.order = res.data;
          this.orderItems = this.order.orderItems || [];
        }
      } catch (err) {
        console.log(err);
      }
    },
  },

  mounted() {
    this.orderID = this.$route.params.id;
    this.token = localStorage.getItem("token");
    this.getOrder();
  },
};
</script>

<style scoped>
h4 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}
.embed-responsive .card-img-top {
  object-fit: cover;
}
</style>
