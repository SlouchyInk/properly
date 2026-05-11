package rent.properly.tenant;

import jakarta.persistence.*;
import lombok.*;
import rent.properly.lease.Lease;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "leases")
@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    @ManyToMany(mappedBy = "tenants")
    private Set<Lease> leases;
}
