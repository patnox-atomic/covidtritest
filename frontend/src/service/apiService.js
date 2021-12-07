import axios from "axios";
const apiClient = {
  async readOrders() {
    //const response = await axios.get("/order");
    axios.get('/order', {
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
        return response.data;
      }
      )
      .catch(error => console.log('Order Get Error:: ' + error))
    return null;
  },
  async createOrder(requestData) {
    const response = await axios.post("/order", requestData);
    return response.data;
  },
  async deleteOrder(orderId) {
    const response = await axios.delete("/order/"+orderId);
    return response.data;
  },
  async updateOrder(orderId, requestData) {
    const response = await axios.patch("/order/"+orderId, requestData);
    return response.data;
  },
  async readProducts() {
    const response = await axios.get("/product");
    return response.data;
  },
  async createProduct(requestData) {
    const response = await axios.post("/product", requestData);
    return response.data;
  },
  async deleteProduct(productId) {
    const response = await axios.delete("/product/"+productId);
    return response.data;
  },
  async updateProduct(productId, requestData) {
    const response = await axios.patch("/product/"+productId, requestData);
    return response.data;
  },
  async readSales() {
    const response = await axios.get("/sale");
    return response.data;
  },
  async createSale(requestData) {
    const response = await axios.post("/sale", requestData);
    return response.data;
  },
  async deleteSale(saleId) {
    const response = await axios.delete("/sale/"+saleId);
    return response.data;
  },
  async updateSale(saleId, requestData) {
    const response = await axios.patch("/sale/"+saleId, requestData);
    return response.data;
  },
  async readUsers() {
    const response = await axios.get("/user");
    return response.data;
  },
  async createUser(requestData) {
    const response = await axios.post("/user", requestData);
    return response.data;
  },
  async deleteUser(userId) {
    const response = await axios.delete("/user/"+userId);
    return response.data;
  },
  async updateUser(userId, requestData) {
    const response = await axios.patch("/user/"+userId, requestData);
    return response.data;
  }
};
export default apiClient;