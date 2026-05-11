package rent.properly.landlord;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LandlordDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<Long> propertyIds = new ArrayList<>();
}
