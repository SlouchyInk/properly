package rent.properly.property;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rent.properly.landlord.Landlord;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    @Mapping(source = "landlord.id", target = "landlordId")
    PropertyDto toDto(Property property);

    @Mapping(target = "landlord", ignore = true)
    Property toEntity(PropertyDto propertyDto);
}
