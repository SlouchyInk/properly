package rent.properly.tenant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rent.properly.lease.Lease;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(target = "leaseIds", source = "leases")
    TenantDto toDto(Tenant tenant);

    @Mapping(target = "leases", ignore = true)
    Tenant toEntity(TenantDto tenantDto);

    default Set<Long> mapLeaseIds(Set<Lease> leases) {
        if (leases == null) {
            return null;
        }

        return leases.stream()
                .map(Lease::getId)
                .collect(Collectors.toSet());
    }
}
