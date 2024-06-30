import http from "../http-common";

class OrderStatusService {
  getAll() {
    return http.get("/orderStatus");
  }

  get(id) {
    return http.get(`/orderStatus/${id}`);
  }

  create(data) {
    return http.post("/orderStatus", data);
  }

  update(id, data) {
    return http.put(`/orderStatus/` +id, data);
  }

  delete(id) {
    return http.delete(`/orderStatus/${id}`);
  }

  deleteAll() {
    return http.delete(`/orderStatus/`);
  }

  findByOrderStatusName(orderStatus) {
    return http.get(`/orderStatus?orderStatus=${orderStatus}`);
  }
}

export default new OrderStatusService();