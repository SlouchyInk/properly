package rent.properly.maintenanceRequest;

public interface MaintenanceRequestService {
    MaintenanceRequestDto createMaintenanceRequest(MaintenanceRequestDto maintenanceRequestDto);
    MaintenanceRequestDto getMaintenanceRequestById(Long id);
    MaintenanceRequestDto updateMaintenanceRequest(Long id, MaintenanceRequestDto maintenanceRequestDto);
    void deleteMaintenanceRequest(Long id);
}
