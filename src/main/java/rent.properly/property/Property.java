package rent.properly.property;

import jakarta.persistence.*;
import lombok.*;
import rent.properly.landlord.Landlord;
import rent.properly.lease.Lease;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "properties")
@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;
}
