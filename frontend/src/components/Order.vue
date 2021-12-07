<template>
  <v-container>
    <h1>Order Management</h1>
    <p>Orders</p>
    <v-row>
      <v-col sm="12">
        <v-alert v-if="responseSuccess" dense text type="success">
          You have successfully added an order
        </v-alert>
      </v-col>
      <v-col sm="4">
        <h3>Order Creation</h3>
        <v-select
          v-model="orderCreation.product_id"
          :items="products"
          label="Choose Product"
          filled
          outlined
          dense
          no-data-text="There are no products"
          value=0
          @change="onChangeProduct"
        ></v-select>
        <v-text-field
          v-model="orderCreation.quantity"
          label="Quantity"
          :rules="quantityRules"
        ></v-text-field>
        <v-btn color="blue" v-on:click="saveOrder">
          Create Order
        </v-btn>
      </v-col>
      <v-col sm="8">
        <h3>Orders</h3>
        <!-- <v-simple-table>
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
                  FullFilled
                </th>
                <th class="text-left">
                  Date Ordered
                </th>
                <th class="text-left">
                  Date FullFilled
                </th>
                <th class="text-left">
                  Product
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="order in orders" :key="order.id">
                <td>{{ order.id }}</td>
                <td>{{ order.quantity }}</td>
                <td>{{ order.is_fullfilled }}</td>
                <td>{{ order.date_ordered }}</td>
                <td>{{ order.date_fullfilled }}</td>
                <td>{{ order.product.name }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table> -->
        <v-data-table
            :headers="headers"
            :items="orders"
            sort-by="id"
            class="elevation-1"
        >
            <template v-slot:top>
                <v-dialog v-model="dialogFullfill" max-width="500px">
                    <v-card>
                        <v-card-title class="text-h5">Are you sure you want to fullfill this order?</v-card-title>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn color="blue darken-1" text @click="closeFullfillDialog">Cancel</v-btn>
                            <v-btn color="blue darken-1" text @click="fullfillOrderConfirm">OK</v-btn>
                            <v-spacer></v-spacer>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </template>
            <template v-slot:item.actions="{ item }">
                <v-btn
                    v-if="item.is_fullfilled==false"
                    color="blue"
                    @click="fullfillOrderCheck(item)"
                >
                    Fullfill
                </v-btn>
            </template>
        </v-data-table>
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
const Querystring = require('querystring');

export default 
{
    name: "Order",
    data: vm => ({
        orderCreation: 
        {
            product_id: 0,
            quantity: "",
        },
        orders: [],
        products: [],
        responseSuccess: false,
        snackbar: false,
        dialogFullfill: false,
        fullfillOrderID: 0,
        displayMessage: "",
        color: 'general',
        headers: [
            { text: 'ID', value: 'id' },
            { text: 'Quantity', value: 'quantity' },
            { text: 'Fulfilled', value: 'is_fullfilled' },
            { text: 'Date Ordered', value: 'date_ordered' },
            { text: 'Date Fulfilled', value: 'date_fullfilled' },
            { text: 'Product Name', value: 'product.name' },
            { text: 'Actions', value: 'actions', sortable: false },
        ],
        quantityRules: [
            v => String(v).length > 0 || 'This field may not be empty',
            v => Number.isInteger(Number(v)) || 'The value must be an integer number',
            v => v > 0 || 'The value must be greater than zero'
        ],
    }),
    methods: {
        getData() {
            this.$http.get('/api/v1/order', {
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
                this.orders = response.data;
            }
            )
            .catch(error => console.log('Order Get Error:: ' + error))
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
            console.log('Got Selected Product As:: ' + JSON.stringify(this.orderCreation.product_id));
        },
        fullfillOrderCheck(item)
        {
            this.fullfillOrderID = item.id;
            this.dialogFullfill = true;
            console.log('Ready to fullfill order:: ' + JSON.stringify(item));
        },
        closeFullfillDialog()
        {
            this.dialogFullfill = false;
        },
        fullfillOrderConfirm()
        {
            this.closeFullfillDialog();
            this.fullFillOrder();
        },
        fullFillOrder()
        {
            //NB: we are sending form data not JSON
            const requestData = {
                orderId: this.fullfillOrderID,
            };
            let body = Querystring['stringify'](requestData);
            const config = {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            }
            this.$http.post('/api/v1/order/fullfill', body, config)
                .then(response => {
                    // console.log('Orders 1:: ' + JSON.stringify(response.data))
                    // console.log('Orders 2:: ' + JSON.stringify(response.data.data))
                    this.getData();
                    this.displayMessage = "Order Fullfilled Successfully";
                    this.snackbar = true;
                    console.log('Order Fullfilled successfully');
                }
            )
            .catch(error => {
                console.log('Order Fullfill Error:: ' + error)
                this.displayMessage = "Error: Failed to Fullfill Order";
                this.snackbar = true;
            })
        },
        saveOrder() {
            const requestData = {
                product_id: this.orderCreation.product_id,
                quantity: this.orderCreation.quantity,
            };
            this.$http.post('/api/v1/order', requestData, {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    },
                })
                .then(response => {
                    // console.log('Orders 1:: ' + JSON.stringify(response.data))
                    // console.log('Orders 2:: ' + JSON.stringify(response.data.data))
                    this.getData();
                    this.displayMessage = "Order Saved Successfully";
                    this.snackbar = true;
                    console.log('Order saved successfully');
                }
            )
            .catch(error => {
                console.log('Order Save Error:: ' + error)
                this.displayMessage = "Error: Failed to Save Order";
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
    watch: {
        orderCreation: {
            handler () {
                console.log('Got Selected Product As:: ' + JSON.stringify(this.orderCreation.product_id))
            }
        },
    },
};
</script>