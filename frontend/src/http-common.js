import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api/v1",
    headers: {
      "Authorization": `Bearer ${localStorage.getItem('token')}`
    }
  });

  axiosInstance.interceptors.request.use(
    (config) => {
      if (config.url.includes('/auth/login') || config.url.includes('/auth/register')) {
        config.headers['Content-Type'] = 'multipart/form-data';
      } else {
        config.headers['Content-Type'] = 'application/json';
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
      if (!error.config.url.includes('/login') && error.response.status === 403) {
        window.location.href = '/403';
      }
      return Promise.reject(error);
    }
  );

  export default axiosInstance;