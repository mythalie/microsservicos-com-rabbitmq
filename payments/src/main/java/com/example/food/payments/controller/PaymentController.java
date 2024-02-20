package com.example.food.payments.controller;

import com.example.food.payments.domain.dto.PaymentResponse;
import com.example.food.payments.service.PaymentService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PaymentResponse> getAll(@PageableDefault(size = 10)Pageable pagination) {
        return service.getAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getId(@PathVariable @NotNull Long id) {
        PaymentResponse paymentId = service.getId(id);

        return ResponseEntity.ok(paymentId);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentResponse response, UriComponentsBuilder uriBuilder) {
        PaymentResponse create = service.createPayment(response);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(create.getId()).toUri();

        //rabbitTemplate.convertAndSend("payment.concluded", create);
        rabbitTemplate.convertAndSend("payments.ex", "", create);
        return ResponseEntity.created(address).body(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentResponse response) {
        PaymentResponse update = service.updatePayment(id, response);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentResponse> deletePayment(@PathVariable @NotNull Long id) {
        service.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
