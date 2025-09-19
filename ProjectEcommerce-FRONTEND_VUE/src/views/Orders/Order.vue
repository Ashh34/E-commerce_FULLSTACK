<template>
  <div class="container">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Your Orders</h4>
      </div>
    </div>

    <div v-for="order in orderList" :key="order.id" class="row mt-2 pt-3 justify-content-around">
      <div class="col-2"></div>

      <div class="col-md-3 embed-responsive embed-responsive-16by9">
        <img :src="order.imageURL" class="w-100 card-img-top embed-responsive-item" />
      </div>

      <div class="col-md-5 px-3">
        <div class="card-block px-3">
          <h6 class="card-title">
            <router-link :to="'/order/' + order.id">Order No : {{ order.id }}</router-link>
          </h6>
          <p class="mb-0">{{ order.totalItems }} item<span v-if="order.totalItems > 1">s</span></p>
          <p id="item-price" class="mb-0 font-weight-bold">Total Cost : $ {{ order.totalCost }}</p>
          <p id="item-total-price">Ordered on : {{ order.orderdate }}</p>
        </div>
      </div>

      <div class="col-2"></div>
      <div class="col-12"><hr /></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "OrderPage",
  props: ["baseURL"],

  data() {
    return {
      token: null,
      orderList: [],
      orders: [],
    };
  },

  methods: {
    async listOrders() {
      try {
        const res = await axios.get(`${this.baseURL}order/?token=${this.token}`);
        if (res.status === 200 && Array.isArray(res.data)) {
          this.orders = res.data;
          this.orderList = this.orders.map((order) => ({
            id: order.id,
            totalCost: order.totalPrice,
            orderdate: (order.createdDate || "").substring(0, 10),
            imageURL: (order.orderItems && order.orderItems[0] && order.orderItems[0].product && order.orderItems[0].product.imageURL) || "",
            totalItems: (order.orderItems && order.orderItems.length) || 0,
          }));
        }
      } catch (e) {
        console.error(e);
      }
    },
  },

  mounted() {
    this.token = localStorage.getItem("token");
    this.listOrders();
  },
};
</script>

<style scoped>
h4,
h5 {
  font-family: "Roboto", sans-serif;
  color: #484848;
  font-weight: 700;
}
.embed-responsive .card-img-top {
  object-fit: cover;
}
</style>
