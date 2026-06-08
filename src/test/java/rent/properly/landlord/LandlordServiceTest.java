package rent.properly.landlord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LandlordServiceTest {

    @Mock
    private LandlordRepository landlordRepository;
    @Mock
    private LandlordMapper landlordMapper;

    private LandlordService landlordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        landlordService = new LandlordServiceImpl(landlordRepository, landlordMapper);
    }

    @Test
    void createLandlordShouldCallRepositorySave() {
        LandlordDto landlordDto = new LandlordDto();
        Landlord landlord = new Landlord();
        Landlord savedLandlord = new Landlord();
        LandlordDto savedLandlordDto = new LandlordDto();

        when(landlordMapper.toEntity(landlordDto)).thenReturn(landlord);
        when(landlordRepository.save(landlord)).thenReturn(savedLandlord);
        when(landlordMapper.toDto(savedLandlord)).thenReturn(savedLandlordDto);

        LandlordDto result = landlordService.createLandlord(landlordDto);

        assertSame(savedLandlordDto, result);
        verify(landlordMapper).toEntity(landlordDto);
        verify(landlordRepository).save(landlord);
        verify(landlordMapper).toDto(savedLandlord);
    }

    @Test
    void getLandlordByIdShouldCallRepositoryFindById() {
        Long landlordId = 1L;
        Landlord landlord = new Landlord();
        LandlordDto landlordDto = new LandlordDto();

        when(landlordRepository.findById(landlordId)).thenReturn(Optional.of(landlord));
        when(landlordMapper.toDto(landlord)).thenReturn(landlordDto);

        LandlordDto result = landlordService.getLandlordById(landlordId);

        assertSame(landlordDto, result);
        verify(landlordRepository).findById(landlordId);
        verify(landlordMapper).toDto(landlord);
    }

    @Test
    void updateLandlordShouldCallRepositorySave() {
        Long landlordId = 1L;
        Landlord existingLandlord = new Landlord();
        Landlord savedLandlord = new Landlord();
        LandlordDto updatedLandlordDto = new LandlordDto();
        LandlordDto savedLandlordDto = new LandlordDto();

        updatedLandlordDto.setFirstName("Jane");
        updatedLandlordDto.setMiddleName("B");
        updatedLandlordDto.setLastName("Smith");
        updatedLandlordDto.setEmail("jane.smith@example.com");
        updatedLandlordDto.setPhoneNumber("555-5678");

        when(landlordRepository.findById(landlordId)).thenReturn(Optional.of(existingLandlord));
        when(landlordRepository.save(existingLandlord)).thenReturn(savedLandlord);
        when(landlordMapper.toDto(savedLandlord)).thenReturn(savedLandlordDto);

        LandlordDto result = landlordService.updateLandlord(landlordId, updatedLandlordDto);

        assertSame(savedLandlordDto, result);
        verify(landlordRepository).findById(landlordId);
        verify(landlordRepository).save(existingLandlord);
        verify(landlordMapper).toDto(savedLandlord);
    }

    @Test
    void deleteLandlordShouldCallRepositoryDelete() {
        Long landlordId = 1L;
        Landlord landlord = new Landlord();

        when(landlordRepository.findById(landlordId)).thenReturn(Optional.of(landlord));

        landlordService.deleteLandlord(landlordId);

        verify(landlordRepository).findById(landlordId);
        verify(landlordRepository).delete(landlord);
    }
}