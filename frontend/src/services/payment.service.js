import http from "../http-common";

class PaymentService {
  getClientSecret(total, userId, orderId) {
    return http.post("client-secret", {
        total,
        userId,
        orderId
    });
  }

  getAll() {
    return http.get('payments');
  }
}

export default new PaymentService();