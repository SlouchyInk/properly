package rent.properly.lease;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rent.properly.property.PropertyMapper;
import rent.properly.tenant.Tenant;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface LeaseMapper {

    @Mapping(target = "tenantIds", source = "tenants")
    LeaseDto toDto(Lease lease);

    @Mapping(target = "tenants", ignore = true)
    Lease toEntity(LeaseDto leaseDto);

    default Set<Long> mapTenantIds(Set<Tenant> tenants) {
        if (tenants == null) {
            return null;
        }

        return tenants.stream()
                .map(Tenant::getId)
                .collect(Collectors.toSet());
    }
}
