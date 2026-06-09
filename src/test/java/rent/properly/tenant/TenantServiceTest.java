package rent.properly.tenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TenantServiceTest {
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private TenantMapper tenantMapper;

    private TenantService tenantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tenantService = new TenantServiceImpl(tenantRepository, tenantMapper);
    }

    @Test
    void createTenantShouldCallRepositorySave() {
        TenantDto tenantDto = new TenantDto();
        Tenant tenant = new Tenant();
        Tenant savedTenant = new Tenant();
        TenantDto savedTenantDto = new TenantDto();

        when(tenantMapper.toEntity(tenantDto)).thenReturn(tenant);
        when(tenantRepository.save(tenant)).thenReturn(savedTenant);
        when(tenantMapper.toDto(savedTenant)).thenReturn(savedTenantDto);

        TenantDto result = tenantService.createTenant(tenantDto);

        assertSame(savedTenantDto, result);
        verify(tenantMapper).toEntity(tenantDto);
        verify(tenantRepository).save(tenant);
        verify(tenantMapper).toDto(savedTenant);
    }

    @Test
    void getTenantByIdShouldCallRepositoryFindById() {
        Long tenantId = 1L;
        Tenant tenant = new Tenant();
        TenantDto tenantDto = new TenantDto();

        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));
        when(tenantMapper.toDto(tenant)).thenReturn(tenantDto);

        TenantDto result = tenantService.getTenantById(tenantId);

        assertSame(tenantDto, result);
        verify(tenantRepository).findById(tenantId);
        verify(tenantMapper).toDto(tenant);
    }

    @Test
    void updateTenantShouldCallRepositorySave() {
        Long tenantId = 1L;
        Tenant existingTenant = new Tenant();
        Tenant savedTenant = new Tenant();
        TenantDto updatedTenantDto = new TenantDto();
        TenantDto savedTenantDto = new TenantDto();

        updatedTenantDto.setEmail("example@email.com");
        updatedTenantDto.setPhoneNumber("555-5555");
        updatedTenantDto.setFirstName("John");
        updatedTenantDto.setLastName("Doe");
        updatedTenantDto.setMiddleName("A");

        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(existingTenant));
        when(tenantRepository.save(existingTenant)).thenReturn(savedTenant);
        when(tenantMapper.toDto(savedTenant)).thenReturn(savedTenantDto);

        TenantDto result = tenantService.updateTenant(tenantId, updatedTenantDto);

        assertSame(savedTenantDto, result);
        verify(tenantRepository).findById(tenantId);
        verify(tenantRepository).save(existingTenant);
        verify(tenantMapper).toDto(savedTenant);
    }

    @Test
    void deleteTenantShouldCallRepositoryDelete() {
        Long tenantId = 1L;
        Tenant tenant = new Tenant();

        when(tenantRepository.findById(tenantId)).thenReturn(Optional.of(tenant));

        tenantService.deleteTenant(tenantId);

        verify(tenantRepository).findById(tenantId);
        verify(tenantRepository).delete(tenant);
    }
}
