import http from "../http-common";

class OrderService {
  getAll() {
    return http.get("/order");
  }

  get(id) {
    return http.get(`/order/${id}`);
  }

  create(data) {
    return http.post("/order", data);
  }

  update(id, data) {
    return http.put(`/order/` +id, data);
  }

  delete(id) {
    return http.delete(`/order/${id}`);
  }

  deleteAll() {
    return http.delete(`/order/`);
  }

}

export default new OrderService();