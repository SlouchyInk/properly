package rent.properly.landlord;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rent.properly.property.Property;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LandlordMapper {

    @Mapping(target = "propertyIds", source = "properties")
    LandlordDto toDto(Landlord landlord);

    @Mapping(target = "properties", ignore = true)
    Landlord toEntity(LandlordDto landlordDto);

    default List<Long> mapPropertyIds(List<Property> properties) {
        if (properties == null) return null;
        return properties.stream().map(Property::getId).collect(Collectors.toList());
    }
}
