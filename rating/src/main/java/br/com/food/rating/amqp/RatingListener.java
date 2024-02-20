package br.com.food.rating.amqp;

import br.com.food.rating.domain.dto.PaymentResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RatingListener {
    @RabbitListener(queues = "payments.details-rating")
    public void receiveMessage(@Payload PaymentResponse payment) {
        if (payment.getNumber().equals("0000")) {
            throw new RuntimeException("Não processado!");
        }
        
        String message = """
                Necessário criar registro de avaliação para o pedido: %s
                Id do pagamento: %s
                Nome do cliente: %s
                Valor R$: %s
                Status: %s
                """.formatted(payment.getOrderId(),
                payment.getId(),
                payment.getName(),
                payment.getValue(),
                payment.getStatus());

        System.out.println(message);
    }
}
