<template>
  <v-app id="inspire">
    <v-app-bar app color="white" flat>
      <v-container class="py-0 fill-height">
        
        <!-- Menu Buttons -->
        <v-btn v-if="getUserRole(['SUPERUSER_ROLE', 'RETAIL_ATTENDANT_ROLE', 'WAREHOUSE_ATTENDANT_ROLE'])" to="/order" text>Orders</v-btn>
        <v-btn v-if="getUserRole(['SUPERUSER_ROLE', 'RETAIL_ATTENDANT_ROLE'])" to="/product" text>Products</v-btn>
        <v-btn v-if="getUserRole(['SUPERUSER_ROLE', 'RETAIL_ATTENDANT_ROLE'])" to="/sale" text>Sales</v-btn>
        <v-btn v-if="getUserRole(['SUPERUSER_ROLE'])" to="/user" text>Users</v-btn>
        <v-btn @click.prevent="logout" text>Logout</v-btn>
        <v-spacer></v-spacer>
        <v-responsive max-width="260"> </v-responsive>
      </v-container>
    </v-app-bar>
    <!-- Main content -->
    <v-main class="grey lighten-3">
      <v-container>
        <v-row>
          <v-col>
            <v-sheet min-height="70vh" rounded="lg">
              <!--  -->
              <router-view></router-view>
            </v-sheet>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "Dashboard",
  data() {
    return {
    };
  },
  methods: {
    logout: function () {
      this.$store.dispatch('logout').then(() => {
        this.$router.push('/login')
      })
    },
    getUserRole(role)
    {
      const roles = localStorage.getItem('scope');
      var found = false;
      for (let i = 0; i < role.length; i++) 
      {
        const search = roles.indexOf(role[i]);
        const ret = (search == -1) ? false : true;
        if(ret)
        {
          found = true;
          break;
        }
      }
      // const search = roles.indexOf(role);
      // const ret = (search == -1) ? false : true;
      return(found);
    },
  },
  mounted() {
  },
};
</script>