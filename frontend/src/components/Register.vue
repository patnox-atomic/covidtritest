<template lang="html">
  <v-app id="inspire">
    <v-app-bar app color="white" flat>
      <v-container class="py-0 fill-height">
        <h1>Register To Evron Foods</h1>
        <v-spacer></v-spacer>
        <v-responsive max-width="260"> </v-responsive>
      </v-container>
    </v-app-bar>
    <!-- Main content -->
    <v-main class="grey lighten-3">
      <v-container>
        <v-row>
          <v-col cols="12" sm="12" md="12" lg="6">
            <v-sheet min-height="70vh" rounded="lg">
              <h4 class="mx-auto" justify-center>Register</h4>
              <v-form>
                <v-text-field
                  v-model="firstname"
                  solo
                  label="First Name"
                  prepend-inner-icon="mdi-account"
                >
                </v-text-field>
                <v-text-field
                  v-model="lastname"
                  solo
                  label="Last Name"
                  prepend-inner-icon="mdi-account"
                >
                </v-text-field>
                <v-text-field
                  v-model="email"
                  solo
                  label="Email"
                  prepend-inner-icon="mdi-email"
                >
                </v-text-field>

                <v-text-field
                  v-model="password1"
                  :type="show1 ? 'text' : 'password'"
                  name="input-10-1"
                  @click:append="show1 = !show1"
                  solo
                  :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                  label="Password"
                  prepend-inner-icon="mdi-lock"
                >
                </v-text-field>

                <v-text-field
                  v-model="password2"
                  :type="show2 ? 'text' : 'password'"
                  name="input-10-1"
                  @click:append="show2 = !show2"
                  solo
                  :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                  label="Repeat Password"
                  prepend-inner-icon="mdi-lock-reset"
                >
                </v-text-field>

                <v-btn
                  :loading="registerLoading"
                  :disabled="registerDisabled"
                  color="blue-grey"
                  class="ma-2 white--text"
                  @click.prevent="register"
                >
                  Register
                  <v-icon right dark>mdi-account</v-icon>
                </v-btn>
                <p class="ma-3 ma-md-1">Have an account ?</p> 
                <v-btn
                  color='blue'
                  class="ma-2 white--text"
                >
                  <router-link to="/login" tag='span'>Login</router-link> 
                </v-btn>
              </v-form>
            </v-sheet>
          </v-col>
        </v-row>
        <v-snackbar
          v-model="snackbar"
          :color="color"
          :top="true"
      >
          {{ displayMessage }}
          <v-btn
              dark
              text
              @click="snackbar = false"
          >
              Close
          </v-btn>
        </v-snackbar>
      </v-container>
    </v-main>
  </v-app>  
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      firstname: '',
      lastname: '',
      email: '',
      password1: '',
      password2: '',
      registerDisabled: false,
      registerLoading: false,
      show1: false,
      show2: false,
      snackbar: false,
      displayMessage: "",
      color: 'general',
    }
  },
  methods: {
    register () {
      if(this.password1 == this.password2)
      {
        const payload = {
          firstName: this.firstname,
          lastName: this.lastname,
          email: this.email,
          password: this.password1
        }
        this.$http.post('/api/v1/registration', payload)
        .then(response => {
            // console.log('Orders 1:: ' + JSON.stringify(response.data))
            // console.log('Orders 2:: ' + JSON.stringify(response.data.data))
            this.displayMessage = "Registered Successfully";
            this.snackbar = true;
            console.log('Registered successfully');
            //this.$router.push('/login');
            this.logout();
        })
        .catch(error => {
            console.log('Failed to register Error:: ' + error)
            this.displayMessage = "Error: Failed to register";
            this.snackbar = true;
        })
      }
      else
      {
        console.log('Error: Passwords must match');
        this.displayMessage = "Error: Passwords must match";
        this.snackbar = true;
      }
    },
    logout: function () {
      this.$store.dispatch('logout').then(() => {
        this.$router.push('/login')
      })
    },
  }
}
</script>

<style lang="css">
</style>