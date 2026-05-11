package rent.properly.landlord;

import jakarta.persistence.*;
import lombok.*;
import rent.properly.property.Property;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "properties")
@Entity
public class Landlord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String middleName;

    private String lastName;

    @OneToMany(mappedBy = "landlord")
    private List<Property> properties = new ArrayList<>();
}
