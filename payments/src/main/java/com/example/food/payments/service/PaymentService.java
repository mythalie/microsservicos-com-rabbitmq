package com.example.food.payments.service;

import com.example.food.payments.domain.dto.PaymentResponse;
import com.example.food.payments.domain.entity.Payment;
import com.example.food.payments.model.Status;
import com.example.food.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentResponse> getAll(Pageable pagination) {
        return repository
                .findAll(pagination)
                .map(p -> modelMapper.map(p, PaymentResponse.class));
    }

    public PaymentResponse getId(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(payment, PaymentResponse.class);
    }

    public PaymentResponse createPayment(PaymentResponse dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CRIADO);
        repository.save(payment);

        return modelMapper.map(payment, PaymentResponse.class);
    }

    public PaymentResponse updatePayment(Long id, PaymentResponse dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Payment with id " + id + " not found");
        }

        // Mapear o DTO para a entidade Payment
        Payment paymentToUpdate = modelMapper.map(dto, Payment.class);

        // Definir o ID do pagamento
        paymentToUpdate.setId(id);

        // Salvar o pagamento atualizado
        Payment updatedPayment = repository.save(paymentToUpdate);

        // Mapear o pagamento atualizado de volta para o DTO de resposta
        return modelMapper.map(updatedPayment, PaymentResponse.class);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
