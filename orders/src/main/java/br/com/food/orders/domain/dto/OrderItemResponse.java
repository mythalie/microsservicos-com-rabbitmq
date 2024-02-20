package br.com.food.orders.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {

    private Long id;
    private Integer amount;
    private String description;
}
