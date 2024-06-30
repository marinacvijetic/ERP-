import http from "../http-common";

class CategoryService {
  getAll() {
    return http.get("/category");
  }

  get(id) {
    return http.get(`/category/${id}`);
  }

  create(data) {
    return http.post("/category", data);
  }

  update(id, data) {
    return http.put(`/category/` +id, data);
  }

  delete(id) {
    return http.delete(`/category/${id}`);
  }

  deleteAll() {
    return http.delete(`/category/`);
  }

  findByCategoryName(categoryName) {
    return http.get(`/category?categoryName=${categoryName}`);
  }
}

export default new CategoryService();