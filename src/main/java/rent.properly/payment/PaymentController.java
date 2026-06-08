package rent.properly.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.created(null).body(paymentService.createPayment(paymentDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable Long id, @RequestBody PaymentDto updatedPayment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, updatedPayment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
