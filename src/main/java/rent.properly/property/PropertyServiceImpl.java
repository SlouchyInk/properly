package rent.properly.property;

import org.springframework.stereotype.Service;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyMapper propertyMapper) {
        this.propertyRepository = propertyRepository;
        this.propertyMapper = propertyMapper;
    }

    @Override
    public PropertyDto createProperty(PropertyDto propertyDto) {
        Property property = propertyRepository.save(propertyMapper.toEntity(propertyDto));
        return propertyMapper.toDto(property);
    }

    @Override
    public PropertyDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property" + id + ""));
        return propertyMapper.toDto(property);
    }

    @Override
    public PropertyDto updateProperty(Long id, PropertyDto updatedProperty) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property" + id + ""));
        property.setAddressLine1(updatedProperty.getAddressLine1());
        property.setAddressLine2(updatedProperty.getAddressLine2());
        property.setCity(updatedProperty.getCity());
        property.setState(updatedProperty.getState());
        property.setZipcode(updatedProperty.getZipcode());

        Property updatedPropertyEntity = propertyRepository.save(property);
        return propertyMapper.toDto(updatedPropertyEntity);
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property" + id + ""));
        propertyRepository.delete(property);
    }
}
