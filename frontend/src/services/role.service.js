import http from "../http-common";

class RoleService {
  getAll() {
    return http.get("/role");
  }
  get(id) {
    return http.get(`/role/${id}`);
  }

  create(data) {
    return http.post("/role", data);
  }

  update(id, data) {
    return http.put(`/role/` +id, data);
  }

  delete(id) {
    return http.delete(`/role/${id}`);
  }

  deleteAll() {
    return http.delete(`/role/`);
  }

  findByRoleName(roleName) {
    return http.get(`/role?roleName=${roleName}`);
  }

}

export default new RoleService();