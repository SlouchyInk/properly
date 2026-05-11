package rent.properly.payment;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private Long leaseId;
    private PaymentStatus status;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDate;
}
