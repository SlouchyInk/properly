package rent.properly.maintenanceRequest;

import org.springframework.stereotype.Service;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class MaintenanceRequestServiceImpl implements MaintenanceRequestService{
    private final MaintenanceRequestRepository maintenanceRequestRepository;
    private final MaintenanceRequestMapper maintenanceRequestMapper;

    public MaintenanceRequestServiceImpl(MaintenanceRequestRepository maintenanceRequestRepository, MaintenanceRequestMapper maintenanceRequestMapper) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
        this.maintenanceRequestMapper = maintenanceRequestMapper;
    }

    @Override
    public MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestDto maintenanceRequestDto) {
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepository.save(maintenanceRequestMapper.toEntity(maintenanceRequestDto));
        return maintenanceRequestMapper.toDto(maintenanceRequest);
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
