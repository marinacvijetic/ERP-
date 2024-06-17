import http from "../http-common";

class AuthService {
  login(data) {
    const formData = new FormData();
    formData.append('userEmail', data.userEmail);
    formData.append('password', data.password);

    return http.post("/auth/login", formData);
  }

  register(data) {
    const formData = new FormData();
    formData.append('userEmail', data.userEmail);
    formData.append('password', data.password);
    formData.append('firstname', data.firstname);
    formData.append('lastname', data.lastname);

    return http.post("/auth/register", formData);
  }
}

const authService = new AuthService();
export default authService;