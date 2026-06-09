package rent.properly.maintenanceRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {
    private final MaintenanceRequestService maintenanceRequestService;
    public MaintenanceRequestController(MaintenanceRequestService maintenanceRequestService) {
        this.maintenanceRequestService = maintenanceRequestService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDto> getMaintenanceRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRequestService.getMaintenanceRequestById(id));
    }

    @PostMapping
    public ResponseEntity<MaintenanceRequestDto> createMaintenanceRequest(@RequestBody MaintenanceRequestDto maintenanceRequestDto) {
        return ResponseEntity.created(null).body(maintenanceRequestService.createMaintenanceRequest(maintenanceRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceRequestDto> updateMaintenanceRequest(@PathVariable Long id, @RequestBody MaintenanceRequestDto updatedMaintenanceRequest) {
        return ResponseEntity.ok(maintenanceRequestService.updateMaintenanceRequest(id, updatedMaintenanceRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceRequest(@PathVariable Long id) {
        maintenanceRequestService.deleteMaintenanceRequest(id);
        return ResponseEntity.noContent().build();
    }
}
