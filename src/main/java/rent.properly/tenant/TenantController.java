package rent.properly.tenant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {
    private final TenantService tenantService;
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PostMapping
    public ResponseEntity<TenantDto> createTenant(@RequestBody TenantDto tenantDto) {
        return ResponseEntity.created(null).body(tenantService.createTenant(tenantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> updateTenant(@PathVariable Long id, @RequestBody TenantDto updatedTenant) {
        return ResponseEntity.ok(tenantService.updateTenant(id, updatedTenant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}
