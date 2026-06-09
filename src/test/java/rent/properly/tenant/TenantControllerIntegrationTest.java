package rent.properly.tenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import rent.properly.ProperlyApplication;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = ProperlyApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class TenantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tenantRepository.deleteAll();
    }

    @Test
    void createTenantShouldReturn201AndSavetoDatabase() throws Exception {
        TenantDto tenantDto = new TenantDto();
        tenantDto.setEmail("example@email.com");
        tenantDto.setFirstName("John");
        tenantDto.setMiddleName("A");
        tenantDto.setLastName("Doe");
        tenantDto.setPhoneNumber("555-1234");

        mockMvc.perform(post("/api/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tenantDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("example@email.com")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.middleName", is("A")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234")));

        assert tenantRepository.count() == 1;
    }

    @Test
    void getTenantByIdShouldReturn200AndTenant() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setEmail("example@email.com");
        tenant.setFirstName("John");
        tenant.setMiddleName("A");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("555-1234");
        Tenant saved = tenantRepository.save(tenant);

        mockMvc.perform(get("/api/tenants/" + saved.getId())
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("example@email.com")))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.middleName", is("A")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234")));
    }

    @Test
    void updateTenantShouldReturn200AndUpdatedTenant() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setEmail("example@email.com");
        tenant.setFirstName("John");
        tenant.setMiddleName("A");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("555-1234");
        Tenant saved = tenantRepository.save(tenant);

        TenantDto updatedTenant = new TenantDto();
        updatedTenant.setEmail("new-example@email.org");
        updatedTenant.setFirstName("Jane");
        updatedTenant.setMiddleName("B");
        updatedTenant.setLastName("Smith");
        updatedTenant.setPhoneNumber("555-5678");

        mockMvc.perform(put("/api/tenants/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTenant)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("new-example@email.org")))
                .andExpect(jsonPath("$.firstName", is("Jane")))
                .andExpect(jsonPath("$.middleName", is("B")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.phoneNumber", is("555-5678")));

        assertEquals("new-example@email.org", updatedTenant.getEmail());
        assertEquals("Jane", updatedTenant.getFirstName());
        assertEquals("B", updatedTenant.getMiddleName());
        assertEquals("Smith", updatedTenant.getLastName());
        assertEquals("555-5678", updatedTenant.getPhoneNumber());
    }

    @Test
    void deleteTenantShouldReturn204AndDeleteFromDatabase() throws Exception {
        Tenant tenant = new Tenant();
        tenant.setEmail("example@email.com");
        tenant.setFirstName("John");
        tenant.setMiddleName("A");
        tenant.setLastName("Doe");
        tenant.setPhoneNumber("555-1234");
        Tenant saved = tenantRepository.save(tenant);

        mockMvc.perform(delete("/api/tenants/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertEquals(0, tenantRepository.count());
        assert !tenantRepository.existsById(saved.getId());
    }
}
