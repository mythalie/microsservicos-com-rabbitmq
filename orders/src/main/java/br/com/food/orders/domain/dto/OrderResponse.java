package br.com.food.orders.domain.dto;

import br.com.food.orders.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemResponse> items = new ArrayList<>();

}
