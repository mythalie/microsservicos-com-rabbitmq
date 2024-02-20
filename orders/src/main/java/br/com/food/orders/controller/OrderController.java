package br.com.food.orders.controller;

import br.com.food.orders.domain.dto.OrderResponse;
import br.com.food.orders.domain.dto.StatusResponse;
import br.com.food.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderResponse> getAll() {
            return service.getAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderResponse> getId(@PathVariable @NotNull Long id) {
            OrderResponse dto = service.getId(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<OrderResponse> create(@RequestBody @Valid OrderResponse dto, UriComponentsBuilder uriBuilder) {
            OrderResponse create = service.create(dto);

            URI address = uriBuilder.path("/requests/{id}").buildAndExpand(create.getId()).toUri();

            return ResponseEntity.created(address).body(create);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody StatusResponse status){
           OrderResponse dto = service.update(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/paidOut")
        public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id) {
            service.approvePaymentRequest(id);

            return ResponseEntity.ok().build();
        }

        @GetMapping("/door")
            public String returnDoor(@Value("${local.server.port}") String door){
            return String.format("Requisição respondida pela instância executando na porta %s", door);
        }
}
