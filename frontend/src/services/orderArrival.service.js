import http from "../http-common";

class OrderArrivalDetailService {
  getAll() {
    return http.get("/orderArrivalDetails");
  }

  get(id) {
    return http.get(`/orderArrivalDetails/${id}`);
  }

  create(data) {
    return http.post("/orderArrivalDetails", data);
  }

  update(id, data) {
    return http.put(`/orderArrivalDetails/` +id, data);
  }

  delete(id) {
    return http.delete(`/orderArrivalDetails/${id}`);
  }

  deleteAll() {
    return http.delete(`/orderArrivalDetails/`);
  }

}

export default new OrderArrivalDetailService();