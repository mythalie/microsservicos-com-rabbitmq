package br.com.food.orders.service;

import br.com.food.orders.domain.dto.OrderResponse;
import br.com.food.orders.domain.dto.StatusResponse;
import br.com.food.orders.domain.entity.Order;
import br.com.food.orders.model.Status;
import br.com.food.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;


    public List<OrderResponse> getAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderResponse.class))
                .collect(Collectors.toList());
    }

    public OrderResponse getId(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderResponse.class);
    }

    public OrderResponse create(OrderResponse dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.REALIZADO);
        order.getItems().forEach(item -> item.setOrder(order));
        Order salvo = repository.save(order);

        return modelMapper.map(order, OrderResponse.class);
    }

    public OrderResponse update(Long id, StatusResponse dto) {

        Order order = repository.idItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderResponse.class);
    }

    public void approvePaymentRequest(Long id) {

        Order order = repository.idItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAGO);
        repository.updateStatus(Status.PAGO, order);
    }
}
