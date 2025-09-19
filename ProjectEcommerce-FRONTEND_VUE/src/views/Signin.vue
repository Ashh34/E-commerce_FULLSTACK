<template>
  <div class="signin-page">
    <div class="signin-card">
      <!-- Logo -->
      <div class="text-center mb-4">
        <router-link :to="{ name: 'Home' }">
          <img id="logo" src="../assets/icon.png" />
        </router-link>
      </div>

      <!-- Title -->
      <h2 class="text-center mb-4">Sign In</h2>

      <!-- Form -->
      <form @submit="signin">
        <div class="form-group mb-3">
          <label>Email</label>
          <input type="email" class="form-control" v-model="email" required />
        </div>
        <div class="form-group mb-3">
          <label>Password</label>
          <input type="password" class="form-control" v-model="password" required />
        </div>

        <small class="form-text text-muted d-block mb-3 text-center">
          By continuing, you agree to BuyBloom's Conditions of Use and Privacy Notice.
        </small>

        <button type="submit" class="btn btn-primary w-100 mb-3">
          Continue
          <div v-if="loading" class="spinner-border spinner-border-sm ms-2" role="status">
            <span class="sr-only">Loading...</span>
          </div>
        </button>
      </form>

      <hr />

      <p class="text-center mt-3">
        <small class="form-text text-muted">New Here?</small>
      </p>
      <router-link :to="{ name: 'Signup' }" class="btn btn-dark w-100">
        Create Account
      </router-link>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import swal from 'sweetalert';
export default {
  name: "SignIn",
  props: ["baseURL"],
  data() {
    return {
      email: null,
      password: null,
      loading: null,
    };
  },
  methods: {
    async signin(e) {
      e.preventDefault();
      this.loading = true;

      const user = {
        email: this.email,
        password: this.password,
      };

      await axios
        .post(`${this.baseURL}user/signIn`, user)
        .then((res) => {
          localStorage.setItem("token", res.data.token);
          this.$emit("fetchData");
          this.$router.push({ name: "Home" });
        })
        .catch((err) => {
          swal({
            text: "Unable to Log you in!",
            icon: "error",
            closeOnClickOutside: false,
          });
          console.log(err);
        })
        .finally(() => {
          this.loading = false;
        });
    },
  },
  mounted() {
    this.loading = false;
  },
};
</script>

<style scoped>
/* Page background */
.signin-page {
  background: linear-gradient(135deg, #fce3ec, #e4d8f9);
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

/* Card container */
.signin-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(181, 101, 167, 0.15);
  padding: 2rem;
  max-width: 400px;
  width: 100%;
}

/* Logo */
#logo {
  width: 120px;
  transition: transform 0.3s ease;
}
#logo:hover {
  transform: scale(1.05);
}

/* Titles & text */
h2 {
  color: #b565a7;
  font-weight: 600;
}
label {
  font-weight: 500;
  color: #4a3f55;
}
small {
  color: #6c6c6c;
}

/* Inputs */
.form-control {
  border-radius: 8px;
  border: 1px solid #ddd;
  background-color: #fafafa;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}
.form-control:focus {
  border-color: #b565a7;
  box-shadow: 0 0 6px rgba(181, 101, 167, 0.3);
}

/* Buttons */
.btn-primary {
  background-color: #b565a7;
  border: none;
  border-radius: 8px;
  font-weight: 500;
  color: #fff;
  transition: background-color 0.3s ease, transform 0.2s ease;
}
.btn-primary:hover {
  background-color: #a14f92;
  transform: translateY(-1px);
}
.btn-dark {
  background-color: #fce3ec;
  color: #b565a7;
  border: none;
  border-radius: 8px;
  font-weight: 500;
}
.btn-dark:hover {
  background-color: #e4d8f9;
}
</style>
