package rent.properly.tenant;

import lombok.Data;

import java.util.Set;

@Data
public class TenantDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private Set<Long> leaseIds;
}
