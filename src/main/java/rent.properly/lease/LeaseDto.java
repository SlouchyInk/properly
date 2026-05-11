package rent.properly.lease;

import lombok.Data;
import rent.properly.property.PropertyDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class LeaseDto {
    private Long id;
    private PropertyDto property;
    private LocalDate moveInDate;
    private LocalDate moveOutDate;
    private BigDecimal rentAmount;
    private LeaseStatus status;
    private LocalDate rentDueDate;
    private BigDecimal securityDeposit;
    private Set<Long> tenantIds;
}
