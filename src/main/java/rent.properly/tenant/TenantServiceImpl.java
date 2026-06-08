package rent.properly.tenant;

import org.springframework.stereotype.Service;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    public TenantServiceImpl(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    @Override
    public TenantDto createTenant(TenantDto tenantDto) {
        Tenant tenant = tenantRepository.save(tenantMapper.toEntity(tenantDto));
        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantDto getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantDto updateTenant(Long id, TenantDto updatedTenant) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
        tenant.setFirstName(updatedTenant.getFirstName());
        tenant.setMiddleName(updatedTenant.getMiddleName());
        tenant.setLastName(updatedTenant.getLastName());
        tenant.setEmail(updatedTenant.getEmail());
        tenant.setPhoneNumber(updatedTenant.getPhoneNumber());

        Tenant updatedTenantEntity = tenantRepository.save(tenant);
        return tenantMapper.toDto(updatedTenantEntity);
    }

    @Override
    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", id));
        tenantRepository.delete(tenant);
    }
}
