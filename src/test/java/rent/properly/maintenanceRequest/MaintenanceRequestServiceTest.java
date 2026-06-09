package rent.properly.maintenanceRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rent.properly.tenant.TenantRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
public class MaintenanceRequestServiceTest {

    @Mock
    private MaintenanceRequestRepository maintenanceRequestRepository;
    @Mock
    private MaintenanceRequestMapper maintenanceRequestMapper;
    private MaintenanceRequestService maintenanceRequestService;
    @Mock
    private TenantRepository tenantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        maintenanceRequestService = new MaintenanceRequestServiceImpl(maintenanceRequestRepository, maintenanceRequestMapper, tenantRepository);
    }

    @Test
    void createMaintenanceRequestShouldCallRepositorySave() {
        MaintenanceRequestDto maintenanceRequestDto = new MaintenanceRequestDto();
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        MaintenanceRequest savedMaintenanceRequest = new MaintenanceRequest();
        MaintenanceRequestDto savedMaintenanceRequestDto = new MaintenanceRequestDto();

        when(maintenanceRequestMapper.toEntity(maintenanceRequestDto)).thenReturn(maintenanceRequest);
        when(maintenanceRequestRepository.save(maintenanceRequest)).thenReturn(savedMaintenanceRequest);
        when(maintenanceRequestMapper.toDto(savedMaintenanceRequest)).thenReturn(savedMaintenanceRequestDto);

        MaintenanceRequestDto result = maintenanceRequestService.createMaintenanceRequest(maintenanceRequestDto);

        assertSame(savedMaintenanceRequestDto, result);
        verify(maintenanceRequestMapper).toEntity(maintenanceRequestDto);
        verify(maintenanceRequestRepository).save(maintenanceRequest);
        verify(maintenanceRequestMapper).toDto(savedMaintenanceRequest);
    }

    @Test
    void getMaintenanceRequestByIdShouldCallRepositoryFindById() {
        Long maintenanceRequestId = 1L;
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        MaintenanceRequestDto maintenanceRequestDto = new MaintenanceRequestDto();

        when(maintenanceRequestRepository.findById(maintenanceRequestId)).thenReturn(Optional.of(maintenanceRequest));
        when(maintenanceRequestMapper.toDto(maintenanceRequest)).thenReturn(maintenanceRequestDto);

        MaintenanceRequestDto result = maintenanceRequestService.getMaintenanceRequestById(maintenanceRequestId);

        assertSame(maintenanceRequestDto, result);
        verify(maintenanceRequestRepository).findById(maintenanceRequestId);
        verify(maintenanceRequestMapper).toDto(maintenanceRequest);
    }

    @Test
    void updateMaintenanceRequestShouldCallRepositorySave() {
        Long maintenanceRequestId = 1L;
        MaintenanceRequest existingMaintenanceRequest = new MaintenanceRequest();
        MaintenanceRequest savedMaintenanceRequest = new MaintenanceRequest();
        MaintenanceRequestDto updatedMaintenanceRequestDto = new MaintenanceRequestDto();
        MaintenanceRequestDto savedMaintenanceRequestDto = new MaintenanceRequestDto();

        updatedMaintenanceRequestDto.setStatus(MaintenanceRequestStatus.COMPLETED);
        updatedMaintenanceRequestDto.setCost(BigDecimal.valueOf(1500.00));
        updatedMaintenanceRequestDto.setDescription("Updated description");
        updatedMaintenanceRequestDto.setDateUpdated(null);
        updatedMaintenanceRequestDto.setDateCreated(null);

        when(maintenanceRequestRepository.findById(maintenanceRequestId)).thenReturn(Optional.of(existingMaintenanceRequest));
        when(maintenanceRequestRepository.save(existingMaintenanceRequest)).thenReturn(savedMaintenanceRequest);
        when(maintenanceRequestMapper.toDto(savedMaintenanceRequest)).thenReturn(savedMaintenanceRequestDto);

        MaintenanceRequestDto result = maintenanceRequestService.updateMaintenanceRequest(maintenanceRequestId, updatedMaintenanceRequestDto);

        assertSame(savedMaintenanceRequestDto, result);
        verify(maintenanceRequestRepository).findById(maintenanceRequestId);
        verify(maintenanceRequestRepository).save(existingMaintenanceRequest);
        verify(maintenanceRequestMapper).toDto(savedMaintenanceRequest);
    }

    @Test
    void deleteMaintenanceRequestShouldCallRepositoryDelete() {
        Long maintenanceRequestId = 1L;
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();

        when(maintenanceRequestRepository.findById(maintenanceRequestId)).thenReturn(Optional.of(maintenanceRequest));

        maintenanceRequestService.deleteMaintenanceRequest(maintenanceRequestId);

        verify(maintenanceRequestRepository).findById(maintenanceRequestId);
        verify(maintenanceRequestRepository).delete(maintenanceRequest);
    }
}
