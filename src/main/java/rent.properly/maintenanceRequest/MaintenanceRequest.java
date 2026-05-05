package rent.properly.maintenanceRequest;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    private String description;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    @Enumerated(EnumType.STRING)
    private MaintenanceRequestStatus status;

    private BigInteger cost;

}
