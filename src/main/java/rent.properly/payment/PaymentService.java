package rent.properly.payment;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto getPaymentById(Long id);
    PaymentDto updatePayment(Long id, PaymentDto paymentDto);
    void deletePayment(Long id);
}
