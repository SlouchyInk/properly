package rent.properly.property;

import lombok.Data;

@Data
public class PropertyDto {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private Long landlordId;
}
