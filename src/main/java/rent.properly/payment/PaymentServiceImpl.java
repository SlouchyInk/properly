package rent.properly.payment;

import org.springframework.stereotype.Service;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = paymentRepository.save(paymentMapper.toEntity(paymentDto));
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        return paymentMapper.toDto(payment);
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentDto updatedPayment) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        payment.setPaymentAmount(updatedPayment.getPaymentAmount());
        payment.setPaymentDate(updatedPayment.getPaymentDate());
        payment.setStatus(updatedPayment.getStatus());

        Payment updatedPaymentEntity = paymentRepository.save(payment);
        return paymentMapper.toDto(updatedPaymentEntity);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", id));
        paymentRepository.delete(payment);
    }
}
