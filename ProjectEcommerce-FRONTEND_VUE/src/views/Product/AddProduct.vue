<template>
  <div class="container my-5">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow-lg border-0 rounded-4">
          <div class="card-header bg-primary text-white text-center rounded-top-4 py-3">
            <h4 class="mb-0">✨ Add New Product</h4>
          </div>
          <div class="card-body p-4">
            <form @submit.prevent="addProduct">
              <!-- Category -->
              <div class="form-group mb-3">
                <label class="fw-bold">Category</label>
                <select class="form-control modern-input" v-model="categoryId" required>
                  <option v-for="category of categories" :key="category.id" :value="category.id">
                    {{ category.categoryName }}
                  </option>
                </select>
              </div>

              <!-- Name -->
              <div class="form-group mb-3">
                <label class="fw-bold">Product Name</label>
                <input
                  type="text"
                  class="form-control modern-input"
                  placeholder="Enter product name"
                  v-model="name"
                  required
                />
              </div>

              <!-- Description -->
              <div class="form-group mb-3">
                <label class="fw-bold">Description</label>
                <textarea
                  class="form-control modern-input"
                  rows="3"
                  placeholder="Write a short description..."
                  v-model="description"
                  required
                ></textarea>
              </div>

              <!-- Image URL -->
              <div class="form-group mb-3">
                <label class="fw-bold">Image URL</label>
                <input
                  type="url"
                  class="form-control modern-input"
                  placeholder="Paste image link here"
                  v-model="imageURL"
                  required
                />
                <div v-if="imageURL" class="text-center mt-3">
                  <img
                    :src="imageURL"
                    alt="Preview"
                    class="img-preview shadow-sm rounded"
                  />
                </div>
              </div>

              <!-- Price -->
              <div class="form-group mb-4">
                <label class="fw-bold">Price (₹)</label>
                <input
                  type="number"
                  class="form-control modern-input"
                  placeholder="Enter price"
                  v-model="price"
                  required
                />
              </div>

              <!-- Submit -->
              <div class="text-center">
                <button type="submit" class="btn-modern w-100">
                  🚀 Add Product
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import swal from "sweetalert";

export default {
  data() {
    return {
      id: null,
      categoryId: null,
      name: null,
      description: null,
      imageURL: null,
      price: null,
    };
  },
  props: ["baseURL", "products", "categories"],
  methods: {
    async addProduct() {
      const newProduct = {
        id: this.id,
        categoryId: this.categoryId,
        name: this.name,
        description: this.description,
        imageURL: this.imageURL,
        price: this.price,
      };

      await axios({
        method: "post",
        url: this.baseURL + "product/add",
        data: JSON.stringify(newProduct),
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then(() => {
          this.$emit("fetchData");
          this.$router.push({ name: "AdminProduct" });
          swal({
            text: "Product Added Successfully!",
            icon: "success",
            closeOnClickOutside: false,
          });
        })
        .catch((err) => console.log(err));
    },
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
  },
};
</script>

<style scoped>
/* Card and Layout */
.card {
  border-radius: 20px;
  overflow: hidden;
}
.card-header {
  font-size: 1.2rem;
  font-weight: 600;
}

/* Inputs */
.modern-input {
  border-radius: 10px;
  border: 1.5px solid #ddd;
  transition: all 0.3s ease-in-out;
  padding: 10px 15px;
}
.modern-input:focus {
  border-color: #007bff;
  box-shadow: 0 0 10px rgba(0, 123, 255, 0.2);
  outline: none;
}

/* Image Preview */
.img-preview {
  max-width: 180px;
  max-height: 180px;
  object-fit: contain;
  margin-top: 10px;
  border: 2px solid #eee;
  border-radius: 12px;
}

/* Button */
.btn-modern {
  background: linear-gradient(135deg, #007bff, #00c6ff);
  color: white;
  font-size: 1rem;
  font-weight: 600;
  padding: 12px;
  border-radius: 12px;
  border: none;
  transition: 0.3s ease;
}
.btn-modern:hover {
  background: linear-gradient(135deg, #0056b3, #0096c7);
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(0, 123, 255, 0.25);
}
</style>
