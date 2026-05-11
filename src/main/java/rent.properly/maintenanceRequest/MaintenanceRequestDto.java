package rent.properly.maintenanceRequest;

import lombok.Data;
import rent.properly.property.PropertyDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaintenanceRequestDto {
    private Long id;
    private PropertyDto property;
    private Long tenantId;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private MaintenanceRequestStatus status;
    private BigDecimal cost;
}
