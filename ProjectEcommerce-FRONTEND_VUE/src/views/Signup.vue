<template>
  <div class="signup-page">
    <div class="signup-card">
      <!-- Logo -->
      <div class="text-center mb-4">
        <router-link :to="{ name: 'Home' }">
          <img id="logo" src="../assets/icon.png" />
        </router-link>
      </div>

      <h2 class="text-center mb-4">Create Account</h2>

      <!-- Signup Form -->
      <form @submit.prevent="signup">
        <div class="form-group mb-3">
          <label>Email</label>
          <input type="email" class="form-control" v-model="email" required />
        </div>

        <div class="row mb-3">
          <div class="col">
            <label>First Name</label>
            <input type="text" class="form-control" v-model="firstName" required />
          </div>
          <div class="col">
            <label>Last Name</label>
            <input type="text" class="form-control" v-model="lastName" required />
          </div>
        </div>

        <div class="form-group mb-3">
          <label>Password</label>
          <input type="password" class="form-control" v-model="password" required />
        </div>

        <div class="form-group mb-4">
          <label>Confirm Password</label>
          <input type="password" class="form-control" v-model="passwordConfirm" required />
        </div>

        <button type="submit" class="btn btn-primary w-100 mb-3">Create Account</button>
      </form>

      <hr />

      <p class="text-center mt-3">
        <small class="form-text text-muted">Already have an account?</small>
      </p>
      <router-link :to="{ name: 'Signin' }" class="btn btn-dark w-100">
        Sign in Here
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import swal from "sweetalert";

export default {
  name: "SignUp",
  data() {
    return {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      passwordConfirm: ""
    };
  },
  methods: {
    async signup() {
      // 1️⃣ Check if passwords match
      if (this.password.trim() !== this.passwordConfirm.trim()) {
        swal({
          text: "Error! Passwords do not match",
          icon: "error",
          closeOnClickOutside: false
        });
        return;
      }

      // 2️⃣ Prepare payload (trim to avoid extra spaces)
      const user = {
        email: this.email.trim(),
        firstName: this.firstName.trim(),
        lastName: this.lastName.trim(),
        password: this.password.trim()
      };

      try {
        // 3️⃣ Make API call to Spring Boot
        const response = await axios.post(
          "http://localhost:8081/user/signup",
          user,
          {
            headers: { "Content-Type": "application/json" }
          }
        );

        // 4️⃣ Show success message
        swal({
          text: response.data?.message || "User signup successful. Please login.",
          icon: "success",
          closeOnClickOutside: false
        });

        // 5️⃣ Redirect to login
        this.$router.replace("/");
      } catch (err) {
        // 6️⃣ Log error for debugging
        console.error("Signup failed:", err.response?.data || err.message);

        // 7️⃣ Show detailed error if available
        swal({
          text: err.response?.data?.message || "Signup failed: Please try again",
          icon: "error",
          closeOnClickOutside: false
        });
      }
    }
  }
};
</script>


<style scoped>
/* Background */
.signup-page {
  background: linear-gradient(135deg, #fce3ec, #e4d8f9);
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

/* Card */
.signup-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(181, 101, 167, 0.15);
  padding: 2rem;
  max-width: 450px;
  width: 100%;
  transition: transform 0.3s ease;
}
.signup-card:hover {
  transform: translateY(-3px);
}

/* Logo */
#logo {
  width: 120px;
  transition: transform 0.3s ease;
}
#logo:hover {
  transform: scale(1.05);
}

/* Headings */
h2 {
  color: #b565a7;
  font-weight: 600;
}

/* Labels */
label {
  font-weight: 500;
  color: #4a3f55;
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
