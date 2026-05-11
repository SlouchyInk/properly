package rent.properly.property;

import jakarta.persistence.*;
import lombok.*;
import rent.properly.landlord.Landlord;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "landlord")
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
