package rent.properly.property;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private PropertyMapper propertyMapper;

    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        propertyService = new PropertyServiceImpl(propertyRepository, propertyMapper);
    }

    @Test
    void createPropertyShouldCallRepositorySave() {
        PropertyDto propertyDto = new PropertyDto();
        Property property = new Property();
        Property savedProperty = new Property();
        PropertyDto savedPropertyDto = new PropertyDto();

        when(propertyMapper.toEntity(propertyDto)).thenReturn(property);
        when(propertyRepository.save(property)).thenReturn(savedProperty);
        when(propertyMapper.toDto(savedProperty)).thenReturn(savedPropertyDto);

        PropertyDto result = propertyService.createProperty(propertyDto);

        assertSame(savedPropertyDto, result);
        verify(propertyMapper).toEntity(propertyDto);
        verify(propertyRepository).save(property);
        verify(propertyMapper).toDto(savedProperty);
    }

    @Test
    void getPropertyByIdShouldCallRepositoryFindById() {
        Long propertyId = 1L;
        Property property = new Property();
        PropertyDto propertyDto = new PropertyDto();

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(propertyMapper.toDto(property)).thenReturn(propertyDto);

        PropertyDto result = propertyService.getPropertyById(propertyId);

        assertSame(propertyDto, result);
        verify(propertyRepository).findById(propertyId);
        verify(propertyMapper).toDto(property);
    }

    @Test
    void updatePropertyShouldCallRepositorySave() {
        Long propertyId = 1L;
        Property existingProperty = new Property();
        Property savedProperty = new Property();
        PropertyDto updatedPropertyDto = new PropertyDto();
        PropertyDto savedPropertyDto = new PropertyDto();

        updatedPropertyDto.setAddressLine1("123 Main St");
        updatedPropertyDto.setAddressLine2("Unit 4");
        updatedPropertyDto.setCity("Springfield");
        updatedPropertyDto.setState("IL");
        updatedPropertyDto.setZipcode("62701");

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(existingProperty)).thenReturn(savedProperty);
        when(propertyMapper.toDto(savedProperty)).thenReturn(savedPropertyDto);

        PropertyDto result = propertyService.updateProperty(propertyId, updatedPropertyDto);

        assertSame(savedPropertyDto, result);
        verify(propertyRepository).findById(propertyId);
        verify(propertyRepository).save(existingProperty);
        verify(propertyMapper).toDto(savedProperty);
    }

    @Test
    void deletePropertyShouldCallRepositoryDelete() {
        Long propertyId = 1L;
        Property property = new Property();

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        propertyService.deleteProperty(propertyId);

        verify(propertyRepository).findById(propertyId);
        verify(propertyRepository).delete(property);
    }
}
