package rent.properly.maintenanceRequest;

import jakarta.persistence.*;
import lombok.*;
import rent.properly.property.Property;
import rent.properly.tenant.Tenant;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"property", "tenant"})
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

    private BigDecimal cost;
}
