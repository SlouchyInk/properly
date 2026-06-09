package rent.properly.maintenanceRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rent.properly.exception.ResourceNotFoundException;
import rent.properly.tenant.TenantRepository;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService{
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final MaintenanceRequestMapper maintenanceRequestMapper;
    private final TenantRepository tenantRepository;

    public MaintenanceRequestServiceImpl(MaintenanceRequestRepository maintenanceRequestRepository,
                                         MaintenanceRequestMapper maintenanceRequestMapper,
                                         TenantRepository tenantRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.maintenanceRequestMapper = maintenanceRequestMapper;
        this.tenantRepository = tenantRepository;
    }

    @Override
    @Transactional
    public MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestDto maintenanceRequestDto) {
        MaintenanceRequest entity = maintenanceRequestMapper.toEntity(maintenanceRequestDto);
        entity.setTenant(tenantRepository.getReferenceById(maintenanceRequestDto.getTenantId()));
        return maintenanceRequestMapper.toDto(maintenanceRequestRepository.save(entity));
    }

    @Override
    public MaintenanceRequestDto getMaintenanceRequestById(Long id) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", id));
        return maintenanceRequestMapper.toDto(maintenanceRequest);
    }

    @Override
    public MaintenanceRequestDto updateMaintenanceRequest(Long id, MaintenanceRequestDto updatedMaintenanceRequest) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", id));
        maintenanceRequest.setStatus(updatedMaintenanceRequest.getStatus());
        maintenanceRequest.setCost(updatedMaintenanceRequest.getCost());
        maintenanceRequest.setDescription(updatedMaintenanceRequest.getDescription());
        maintenanceRequest.setDateCreated(updatedMaintenanceRequest.getDateCreated());
        maintenanceRequest.setDateUpdated(updatedMaintenanceRequest.getDateUpdated());

        MaintenanceRequest updatedMaintenanceRequestEntity = maintenanceRequestRepository.save(maintenanceRequest);
        return maintenanceRequestMapper.toDto(updatedMaintenanceRequestEntity);
    }

    @Override
    public void deleteMaintenanceRequest(Long id) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceRequest", id));
        maintenanceRequestRepository.delete(maintenanceRequest);
    }
}
