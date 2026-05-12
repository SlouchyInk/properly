package rent.properly.property;

public interface PropertyService {
    PropertyDto createProperty(PropertyDto propertyDto);
    PropertyDto getPropertyById(Long id);
    PropertyDto updateProperty(Long id, PropertyDto propertyDto);
    void deleteProperty(Long id);
}
