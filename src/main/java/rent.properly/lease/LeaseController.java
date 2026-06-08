package rent.properly.lease;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {
    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaseDto> getLeaseById(@PathVariable Long id) {
        return ResponseEntity.ok(leaseService.getLeaseById(id));
    }

    @PostMapping
    public ResponseEntity<LeaseDto> createLease(@RequestBody LeaseDto leaseDto) {
        return ResponseEntity.created(null).body(leaseService.createLease(leaseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaseDto> updateLease(@PathVariable long id, @RequestBody LeaseDto updatedLease) {
        return ResponseEntity.ok(leaseService.updateLease(id, updatedLease));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLease(@PathVariable long id) {
        leaseService.deleteLease(id);
        return ResponseEntity.noContent().build();
    }
}
