package rent.properly.lease;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rent.properly.property.Property;
import rent.properly.tenant.Tenant;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    private LocalDateTime moveInDate;

    private LocalDateTime moveOutDate;

    private BigInteger rentAmount;

    private Enum status;

    private LocalDateTime rentDueDate;

    private BigInteger securityDeposit;

    @ManyToMany
    @JoinTable(
            name = "tenant_lease",
            joinColumns = @JoinColumn(name = "lease_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private Set<Tenant> tenants;
}
