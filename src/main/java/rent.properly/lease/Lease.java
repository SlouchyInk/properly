package rent.properly.lease;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rent.properly.payment.Payment;
import rent.properly.property.Property;
import rent.properly.tenant.Tenant;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    private LocalDate moveInDate;

    private LocalDate moveOutDate;

    private BigDecimal rentAmount;

    @Enumerated(EnumType.STRING)
    private LeaseStatus status;

    private LocalDate rentDueDate;

    private BigDecimal securityDeposit;

    @ManyToMany
    @JoinTable(
            name = "tenant_lease",
            joinColumns = @JoinColumn(name = "lease_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private Set<Tenant> tenants;

    @OneToMany(mappedBy = "lease")
    private List<Payment> paymentIds = new ArrayList<>();
}
