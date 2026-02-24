package rent.properly.maintenanceRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rent.properly.property.Property;
import rent.properly.tenant.Tenant;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Property property;

    private Tenant tenant;

    private String description;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    private Enum status;

    private BigInteger cost;

}
