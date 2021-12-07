<template>
  <v-container>
    <h1>Product Management</h1>
    <p>Products</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          You have successfully added a product
        </v-alert>
      </v-col>
      <v-col sm="6">
        <h3>Product Creation</h3>
        <v-text-field
          v-model="productCreation.name"
          label="Name"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.description"
          label="Description"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.barcode"
          label="Barcode"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.price"
          label="Price"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.quantity"
          label="Quantity"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.reorder_level"
          label="Reorder Level"
        ></v-text-field>
        <v-text-field
          v-model="productCreation.reorder_quantity"
          label="Reorder Quantity"
        ></v-text-field>
        <v-btn color="blue" v-on:click="createProduct">
          Create Product
        </v-btn>
      </v-col>
      <v-col sm="6">
        <h3>Products</h3>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
                  ID
                </th>
                <th class="text-left">
                  Name
                </th>
                <th class="text-left">
                  Description
                </th>
                <th class="text-left">
                  Barcode
                </th>
                <th class="text-left">
                  Price
                </th>
                <th class="text-left">
                  Quantity
                </th>
                <th class="text-left">
                  Reorder Level
                </th>
                <th class="text-left">
                  Reorder Quantity
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="product in products" :key="product.id">
                <td>{{ product.id }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.description }}</td>
                <td>{{ product.barcode }}</td>
                <td>{{ product.price }}</td>
                <td>{{ product.quantity }}</td>
                <td>{{ product.reorder_level }}</td>
                <td>{{ product.reorder_quantity }}</td>
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
    name: "Product",
    data: vm => ({
        productCreation: 
        {
            name: "",
            description: "",
            barcode: "",
            price: "",
            quantity: "",
            reorder_level: "",
            reorder_quantity: "",
        },
        products: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/product', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                ////this.total_items = Number(response.data.meta.querytotal);
                ////this.payload = response.data.data;
                ////this.loading = false;
                // console.log('products 1:: ' + JSON.stringify(response.data))
                // console.log('products 2:: ' + JSON.stringify(response.data.data))
                this.products = response.data;
            }
            )
            .catch(error => console.log('Products Get Error:: ' + error))
        },
        createProduct() {
            const requestData = {
                name: this.productCreation.name,
                description: this.productCreation.description,
                barcode: this.productCreation.barcode,
                price: this.productCreation.price,
                quantity: this.productCreation.quantity,
                reorder_level: this.productCreation.reorder_level,
                reorder_quantity: this.productCreation.reorder_quantity
            };
            this.$http.post('/api/v1/product', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    ////this.total_items = Number(response.data.meta.querytotal);
                    ////this.payload = response.data.data;
                    ////this.loading = false;
                    // console.log('Orders 1:: ' + JSON.stringify(response.data))
                    // console.log('Orders 2:: ' + JSON.stringify(response.data.data))
                    //this.orders = response.data;
                    this.getData();
                    this.displayMessage = "Product Saved Successfully";
                    this.snackbar = true;
                    console.log('Product saved successfully');
                }
            )
            .catch(error => {
                console.log('Product Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save Product";
                this.snackbar = true;
            })
        },
    },
    mounted() {
        //this.readProducts();
        this.getData();
    },
    created() {
        //this.readProducts();
        this.getData();
    },
};
</script>