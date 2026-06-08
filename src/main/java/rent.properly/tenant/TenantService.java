package rent.properly.tenant;

public interface TenantService {
    TenantDto createTenant(TenantDto tenantDto);
    TenantDto getTenantById(Long id);
    TenantDto updateTenant(Long id, TenantDto tenantDto);
    void deleteTenant(Long id);
}
