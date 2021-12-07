<template>
  <v-container>
    <h1>Sale Management</h1>
    <p>Sales</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          You have successfully added a sale
        </v-alert>
      </v-col>
      <v-col sm="6">
        <h3>Sale Creation</h3>
        <v-select
          v-model="saleCreation.product_id"
          :items="products"
          label="Choose Product"
          filled
          outlined
          dense
          no-data-text="There are no products"
          value=0
          @change="onChangeProduct"
          required
        ></v-select>
        <v-text-field
          v-model="saleCreation.quantity"
          label="Quantity"
          :rules="quantityRules"
          required
        ></v-text-field>
        <v-text-field
          v-model="saleCreation.price"
          label="Price"
          :rules="priceRules"
          required
        ></v-text-field>
        <v-btn color="blue" v-on:click="saveSale">
          Create Sale
        </v-btn>
      </v-col>
      <v-col sm="6">
        <h3>Sales</h3>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
                  ID
                </th>
                <th class="text-left">
                  Quantity
                </th>
                <th class="text-left">
                  Price
                </th>
                <th class="text-left">
                  Sale Date
                </th>
                <th class="text-left">
                  Product
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="sale in sales" :key="sale.id">
                <td>{{ sale.id }}</td>
                <td>{{ sale.quantity }}</td>
                <td>{{ sale.price }}</td>
                <td>{{ sale.sale_date }}</td>
                <td>{{ sale.product.name }}</td>
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
    name: "Sale",
    data: vm => ({
        saleCreation: 
        {
            product_id: "",
            quantity: "",
            price: ""
        },
        sales: [],
        products: [],
        responseSuccess: false,
        snackbar: false,
        displayMessage: "",
        color: 'general',
        quantityRules: [
            v => String(v).length > 0 || 'This field may not be empty',
            v => Number.isInteger(Number(v)) || 'The value must be an integer number',
            v => v > 0 || 'The value must be greater than zero'
        ],
        priceRules: [
            v => String(v).length > 0 || 'This field may not be empty',
            //v => Number.isInteger(Number(v)) || 'The value must be an integer number',
            v => v > 0 || 'The value must be greater than zero'
        ],
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/sale', {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            })
            .then(response => {
                ////this.total_items = Number(response.data.meta.querytotal);
                ////this.payload = response.data.data;
                ////this.loading = false;
                // console.log('Sales 1:: ' + JSON.stringify(response.data))
                // console.log('Sales 2:: ' + JSON.stringify(response.data.data))
                this.sales = response.data;
            }
            )
            .catch(error => console.log('Sale Get Error:: ' + error))
        },
        getProducts() {
            this.$http.get('/api/v1/product', {
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
                //this.products = response.data;
                // this.product_names = this.decodeTypeNames(response.data);
                // this.product_values = this.decodeTypeIds(response.data);
                // this.products = this.getProductIds();
                this.products = this.getProductIds(this.decodeProductNames(response.data), this.decodeProductIds(response.data));
            }
            )
            .catch(error => console.log('Products Get Error:: ' + error))
        },
        getProductIds(product_names, product_ids)
        {
            let tmp = [];
            // let product_values = this.product_values;
            // let product_names = this.product_names;
            for(let i = 0; i < product_ids.length; i++)
            {
                tmp.push({'value': product_ids[i], 'text': product_names[i]});
            }
            // console.log('Got Final Product IDs As:: ' + JSON.stringify(tmp));
            return(tmp);
        },
        decodeProductNames(data) {
            let types = data.map(p => p.name);
            // console.log('Got Product Names As:: ' + JSON.stringify(types));
            return(types);
        },
        decodeProductIds(data) {
            let ids = data.map(p => p.id);
            // console.log('Got Product IDs As:: ' + JSON.stringify(ids));
            return(ids);
        },
        onChangeProduct()
        {
            console.log('Got Selected Product As:: ' + JSON.stringify(this.saleCreation.product_id));
        },
        saveSale() {
            const requestData = {
                product_id: this.saleCreation.product_id,
                quantity: this.saleCreation.quantity,
                price: this.saleCreation.price,
            };
            this.$http.post('/api/v1/sale', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    // console.log('Orders 1:: ' + JSON.stringify(response.data))
                    // console.log('Orders 2:: ' + JSON.stringify(response.data.data))
                    this.getData();
                    this.displayMessage = "Sale Saved Successfully";
                    this.snackbar = true;
                    console.log('Sale saved successfully');
                }
            )
            .catch(error => {
                console.log('Sale Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save Sale";
                this.snackbar = true;
            })
        },
    },
    mounted() {
        this.getData();
        this.getProducts();
    },
    created() {
        this.getData();
        this.getProducts();
    },
};
</script>