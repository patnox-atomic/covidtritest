<template lang="html" class="primary_bg">
    <v-app id="inspire">
        <div 
            class="primary_bg"
            style="height: 100vh; max-height: 100%;"
        >
            <v-main fluid>
            <v-container 
                full-height 
                fluid 
                class="mb-15"
            >
                <v-layout row>
                    <v-flex md6 lg6 sm12 xs12 x6>
                        <img src="../assets/images/3.png" width="95%">
                    </v-flex>
                    <v-flex md6 lg6 sm12 xs12 x6 pl-3>
                        <h1 class="white--text font-weight-medium mt-16 d-sm-pl-8 d-xs-pl-6">CDC<span class="red--text"> Kenya</span></h1>
                        <h1 class="white--text font-weight-medium mt-5 d-sm-pl-8 d-xs-pl-6">Covid<span class="red--text"> Triage</span></h1>
                        <h2 class="white--text d-sm-pl-6">Discover diseases <span class="yellow--text"> and treat them </span></h2>
                        <p class="white--text d-sm-pl-6">Centers For Disease Control monitors diseases all over the world. However to get started login to your existing account or create one if you dont have.</p>
                        <v-dialog ref="loginDialog" v-model="dialog" scrollable max-width="500px">
                            <!-- Login popup modal -->
                            <template v-slot:activator="{ on, attrs }">
                            <v-btn
                                color="black--text white"
                                dark
                                v-bind="attrs"
                                v-on="on"
                                ref="loginDialogButton"
                            >
                                Login
                            </v-btn>
                            </template>
                            <v-card>
                            <v-card-title>Login</v-card-title>
                            <v-divider></v-divider>
                            <v-card-text class="mt-2">
                            <v-text-field
                                v-model="email"
                                solo
                                label="Email"
                                prepend-inner-icon="mdi-email"
                            ></v-text-field>

                            <!-- Allowing user to toggle visibility of password -->
                            <v-text-field
                                v-model="password"
                                :type="showPassword ? 'text' : 'password'"
                                name="input-10-1"
                                @click:append="showPassword = !showPassword"
                                solo
                                :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                                label="Password"
                                prepend-inner-icon="mdi-account-key"
                            ></v-text-field>
                            </v-card-text>
                            <v-divider></v-divider>
                            <v-card-actions>
                                <v-btn ref="buttonCloseLoginDialog" color="blue darken-1" text @click="dialog = false">Close</v-btn>
                                <v-btn ref="buttonLogintoApp" color="blue darken-1" text @click.prevent="login">login</v-btn>
                            </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-btn class="ml-2" ref="registerButton" outlined color="white" to="/register">
                            Register
                        </v-btn>
                        <v-snackbar
                            v-model="snackbar"
                            :color="color"
                            :top="true"
                            >
                            {{ errorMessages }}
                            <v-btn
                                dark
                                text
                                @click="snackbar = false"
                            >
                                Close
                            </v-btn>
                        </v-snackbar>
                    </v-flex>
                </v-layout>
            </v-container>
            </v-main>
            <v-footer
                color="primary_bg"
                padless
            >
                <v-row
                    justify="center"
                    no-gutters
                >
                <v-col
                    class="text-center white--text"
                    cols="12"
                >
                    {{ new Date().getFullYear() }} — <strong>CDC Kenya</strong>
                    <p>Patnox</p>
                </v-col>
                </v-row>
            </v-footer>
        </div>
    </v-app>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      showPassword: false,
      email: '',
      password: '',
      dialog: false,
      errorMessages: 'Incorrect login info',
      snackbar: false,
      color: 'general',
    }
  },
  // Sends action to Vuex that will log you in and redirect to the dash otherwise, error
  methods: {
    login: function () {
      let username = this.email
      let password = this.password
      this.$store.dispatch('login', { username, password })
        .then(() => this.$router.push('/'))
        .catch(err => {
          console.log(err)
          this.snackbar = true
        }
        )
    }
  },
  mounted() {
      //console.log("Document Refs: " + Object.keys(this.$refs));
  },
  metaInfo () {
    return {
      title: 'Evron Foods | Login'
    }
  }
}
</script>

<style lang="css" scoped>
.primary_bg{
  background-color:#9921e8;
  background-image:linear-gradient(315deg, #9921e8 0%, #5f72be 74%);
}

.v-text-field{
      width: 400px;
}
</style>