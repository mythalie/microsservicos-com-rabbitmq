package br.com.food.orders.domain.dto;

import br.com.food.orders.model.StatusPayment;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponse {
    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private StatusPayment status;
    private Long orderId;
    private Long paymentMethodId;
}
