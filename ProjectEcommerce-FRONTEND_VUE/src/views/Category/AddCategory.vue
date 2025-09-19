<template>
  <div class="add-category-page">
    <div class="row">
      <div class="col-12 text-center">
        <h4 class="pt-3">Add New Category</h4>
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col-md-6 form-card">
        <form @submit.prevent="addCategory">
          <div class="form-group">
            <label>Category Name</label>
            <input
              type="text"
              class="form-control"
              v-model="categoryName"
              required
            />
          </div>
          <div class="form-group">
            <label>Description</label>
            <textarea
              class="form-control"
              v-model="description"
              rows="3"
              required
            ></textarea>
          </div>
          <div class="form-group">
            <label>Image URL</label>
            <input
              type="url"
              class="form-control"
              v-model="imageURL"
              required
            />
          </div>
          <button type="submit" class="btn btn-submit w-100">
            Submit
          </button>
        </form>
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
      categoryName: null,
      description: null,
      imageURL: null
    };
  },
  props: ["baseURL", "categories"],
  methods: {
    async addCategory() {
      const newCategory = {
        categoryName: this.categoryName,
        description: this.description,
        imageUrl: this.imageURL
      };

      try {
        await axios.post(
          this.baseURL + "api/categories/create",
          JSON.stringify(newCategory),
          {
            headers: { "Content-Type": "application/json" }
          }
        );

        this.$emit("fetchData");
        this.$router.push({ name: "AdminCategory" });
        swal({
          text: "Category Added Successfully!",
          icon: "success",
          closeOnClickOutside: false
        });
      } catch (err) {
        console.error(err);
        swal({
          text: err.response?.data?.message || "Failed to add category",
          icon: "error"
        });
      }
    }
  },
  mounted() {
    if (!localStorage.getItem("token")) {
      this.$router.push({ name: "Signin" });
    }
  }
};
</script>


<style scoped>
.add-category-page {
  background: linear-gradient(135deg, #fce3ec, #e4d8f9);
  min-height: 100vh;
  padding: 2rem 1rem;
}

h4 {
  font-family: "Roboto", sans-serif;
  color: #b565a7;
  font-weight: 700;
}

/* Form Card */
.form-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(181, 101, 167, 0.15);
}

/* Labels */
label {
  font-weight: 500;
  color: #6a4c93;
}

/* Inputs & Textarea */
.form-control {
  border: 1px solid #d8b4e2;
  border-radius: 8px;
  transition: 0.2s ease;
}
.form-control:focus {
  border-color: #b565a7;
  box-shadow: 0 0 5px rgba(181, 101, 167, 0.4);
}

/* Submit Button */
.btn-submit {
  background-color: #b565a7;
  color: #fff;
  font-weight: 500;
  border-radius: 8px;
  padding: 10px;
  transition: background-color 0.3s ease, transform 0.2s ease;
}
.btn-submit:hover {
  background-color: #a14f92;
  transform: translateY(-1px);
}
</style>
