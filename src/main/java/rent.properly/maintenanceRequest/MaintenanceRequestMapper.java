package rent.properly.maintenanceRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rent.properly.property.PropertyMapper;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class})
public interface MaintenanceRequestMapper {

    @Mapping(target = "tenantId", source = "tenant.id")
    MaintenanceRequestDto toDto(MaintenanceRequest maintenanceRequest);

    @Mapping(target = "tenant", ignore = true)
    MaintenanceRequest toEntity(MaintenanceRequestDto maintenanceRequestDto);

}
