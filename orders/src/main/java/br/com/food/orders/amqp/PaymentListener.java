package br.com.food.orders.amqp;

import br.com.food.orders.domain.dto.PaymentResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    //@RabbitListener(queues = "payment.concluded")
    @RabbitListener(queues = "payments.details-order")
    public void receiveMessage(PaymentResponse payment){
        String message =
           """
            Dados do pagamento: %s
            Número do pedido: %s
            Valor R$: %s
            Status: %s
           """.formatted(payment.getId(), payment.getOrderId(), payment.getValue(), payment.getStatus());
        System.out.println("Recebi a mensagem: " + message);
    }
}
