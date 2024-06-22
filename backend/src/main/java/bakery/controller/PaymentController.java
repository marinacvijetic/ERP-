package bakery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import bakery.model.TblPayment;
import bakery.request.OrderResponse;
import bakery.request.PaymentResponse;
import bakery.service.PaymentService;

@RestController
public class PaymentController {
	
    @Value("${stripe.pk}")
    private String stripePrivateKey;
    
    @Autowired
    PaymentService paymentService;
    
    @GetMapping("/client-secret")
    public ClientSecretResponse getClientSecret(@RequestBody ClientSecretRequest request) throws StripeException {
        // Set your Stripe secret key
        Stripe.apiKey = stripePrivateKey;

        // Create a PaymentIntent with the necessary parameters
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
                .setAmount(request.getTotal() * 100) // saved in cents, so we have to multiply with 100
                .putMetadata("orderId", request.getOrderId())
                .setCurrency("rsd")
                .build();

        PaymentIntent paymentIntent = PaymentIntent.create(createParams);

        // Return the client secret to the client-side
        return new ClientSecretResponse(paymentIntent.getClientSecret());
    }

    static class ClientSecretRequest {
        private long total;
        private String orderId;

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }


        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    static class ClientSecretResponse {
        private String clientSecret;

        public ClientSecretResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getClientSecret() {
            return clientSecret;
        }
    }

    @GetMapping("payments")
    public List<PaymentResponse> findAll() {
        List<TblPayment> payments = paymentService.findAll();
        List<PaymentResponse> responses = payments.stream().map(x -> {
            PaymentResponse response = new PaymentResponse();
            BeanUtils.copyProperties(x, response);
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderId(x.getOrder().getOrderId());
            orderResponse.setUser(x.getOrder().getUser());
            response.setOrder(orderResponse);
            return response;
        }).collect(Collectors.toList());

        return responses;
    }

}
