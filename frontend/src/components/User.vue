<template>
  <v-container>
    <h1>User Management</h1>
    <p>Users</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          You have successfully added an user
        </v-alert>
      </v-col>
      <v-col
        cols="12"
        sm="4"
      >
        <h3>User Creation</h3>
        <v-text-field
          v-model="userCreation.firstname"
          label="First Name"
        ></v-text-field>
        <v-text-field
          v-model="userCreation.lastname"
          label="Last Name"
        ></v-text-field>
        <v-text-field
          v-model="userCreation.email"
          label="Email"
          :rules="emailRules"
        ></v-text-field>
        <v-text-field
          v-model="userCreation.password"
          label="Password"
        ></v-text-field>
        <v-row
          class="grey lighten-5 mb-6"
        >
          <v-col
            cols="12"
            sm="6"
          >
            <v-select
              v-model="userCreation.roles"
              :items="userRoles"
              label="Select User Roles"
              multiple
              chips
              hint="Select User Roles"
              persistent-hint
            ></v-select>
          </v-col>
        </v-row>
        <v-btn color="blue" v-on:click="saveUser">
          Create User
        </v-btn>
      </v-col>
      <v-col 
        sm="8"
      >
        <h3>Users</h3>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
                  ID
                </th>
                <th class="text-left">
                  First Name
                </th>
                <th class="text-left">
                  Last Name
                </th>
                <th class="text-left">
                  Email
                </th>
                <th class="text-left">
                  User Role
                </th>
                <th class="text-left">
                  Locked
                </th>
                <th class="text-left">
                  Enabled
                </th>
                <th class="text-left">
                  User Name
                </th>
                <th class="text-left">
                  Not Expired
                </th>
                <th class="text-left">
                  Not Locked
                </th>
                <th class="text-left">
                  Credentials Not Expired
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in users" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.firstName }}</td>
                <td>{{ user.lastName }}</td>
                <td>{{ user.email }}</td>
                <td>{{ user.appUserRole }}</td>
                <td>{{ user.locked }}</td>
                <td>{{ user.enabled }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.accountNonExpired }}</td>
                <td>{{ user.accountNonLocked }}</td>
                <td>{{ user.credentialsNonExpired }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
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
</template>

<script>
import api from "@/service/apiService";

export default 
{
    name: "User",
    data: vm => ({
        userCreation: 
        {
            firstname: "",
            lastname: "",
            email: "",
            password: "",
            roles: "",
        },
        users: [],
        userRoles: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
        emailRules: [
          value => !!value || 'Required.',
          value => (value || '').length <= 64 || 'Max 64 characters',
          value => {
            const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            return pattern.test(value) || 'Invalid e-mail.'
          },
        ],
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/user', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                // console.log('Users 1:: ' + JSON.stringify(response.data))
                // console.log('Users 2:: ' + JSON.stringify(response.data.data))
                this.users = response.data;
            }
            )
            .catch(error => console.log('User Get Error:: ' + error))
        },
        saveUser() {
            let role_ids = [];
            let roles = this.userCreation.roles;
            for(let i = 0; i < roles.length; i++)
            {
                role_ids.push({'id': roles[i], 'name': this.getRoleNameGivenId(roles[i])});
            }
            //console.log('Role IDs:: ' + JSON.stringify(role_ids));
            const requestData = {
                firstName: this.userCreation.firstname,
                lastName: this.userCreation.lastname,
                email: this.userCreation.email,
                password: this.userCreation.password,
                //appUserRoles: role_ids,
                //appUserRoles: JSON.stringify(role_ids),
                // appUserRoles: [
                //   {
                //     id: 8,
                //     name: 'RETAIL_ATTENDANT_ROLE'
                //   }
                // ],
            };
            if(roles.length > 0)
            {
              requestData.appUserRoles = role_ids;
            }
            //console.log('Payload:: ' + JSON.stringify(requestData));
            this.$http.post('/api/v1/user', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    this.getData();
                    this.displayMessage = "User Saved Successfully";
                    this.snackbar = true;
                    console.log('User saved successfully');
                }
            )
            .catch(error => {
                console.log('User Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save User";
                this.snackbar = true;
            })
        },
        getRoles() {
            this.$http.get('/api/v1/user/role', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                this.userRoles = this.getRoleIds(this.decodeRoleNames(response.data), this.decodeRoleIds(response.data));
            }
            )
            .catch(error => console.log('Products Get Error:: ' + error))
        },
        getRoleIds(role_names, role_ids)
        {
            let tmp = [];
            for(let i = 0; i < role_ids.length; i++)
            {
                tmp.push({'value': role_ids[i], 'text': role_names[i]});
            }
            // console.log('Got Final Role IDs As:: ' + JSON.stringify(tmp));
            return(tmp);
        },
        decodeRoleNames(data) {
            let types = data.map(p => p.name);
            // console.log('Got Role Names As:: ' + JSON.stringify(types));
            return(types);
        },
        decodeRoleIds(data) {
            let ids = data.map(p => p.id);
            // console.log('Got Role IDs As:: ' + JSON.stringify(ids));
            return(ids);
        },
        getRoleNameGivenId(roleId) {
            let ret = '';
            let pl = this.userRoles;
            for(let i = 0; i < pl.length; i++)
            {
              if(pl[i].value == roleId)
              {
                ret = pl[i].text;
                break;
              }
            }
            return(ret);
        },
    },
    mounted() {
        this.getData();
        this.getRoles();
    },
    created() {
        this.getData();
        this.getRoles();
    },
};
</script>