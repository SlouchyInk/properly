package rent.properly.maintenanceRequest;

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
import rent.properly.lease.LeaseRepository;
import rent.properly.property.Property;
import rent.properly.property.PropertyDto;
import rent.properly.property.PropertyMapper;
import rent.properly.property.PropertyRepository;
import rent.properly.tenant.Tenant;
import rent.properly.tenant.TenantRepository;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

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
public class MaintenanceRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Property testProperty;
    private Tenant testTenant;
    private PropertyDto testPropertyDto;
    @Autowired
    private PropertyMapper propertyMapper;
    @Autowired
    private LeaseRepository leaseRepository;

    @BeforeEach
    void setUp() {
        maintenanceRequestRepository.deleteAll();
        leaseRepository.deleteAll();
        tenantRepository.deleteAll();
        propertyRepository.deleteAll();

        // Create test data
        testProperty = new Property();
        testProperty.setAddressLine1("123 Main Street");
        testProperty.setCity("Springfield");
        testProperty.setState("IL");
        testProperty.setZipcode("62701");
        testProperty = propertyRepository.save(testProperty);

        testPropertyDto = propertyMapper.toDto(testProperty);

        testTenant = new Tenant();
        testTenant.setFirstName("John");
        testTenant.setLastName("Doe");
        testTenant.setEmail("john@example.com");
        testTenant.setPhoneNumber("555-1234");
        testTenant = tenantRepository.save(testTenant);
    }

    @Test
    void createMaintenanceRequestShouldReturn201() throws Exception {
        MaintenanceRequestDto requestDto = new MaintenanceRequestDto();
        requestDto.setDescription("Fix broken window");
        requestDto.setProperty(testPropertyDto);
        requestDto.setTenantId(testTenant.getId());
        requestDto.setStatus(MaintenanceRequestStatus.IN_PROGRESS);
        requestDto.setCost(new BigDecimal("150.00"));

        mockMvc.perform(post("/api/maintenance-requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Fix broken window")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.cost", is(150.00)))
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, maintenanceRequestRepository.count());
    }

    @Test
    void getMaintenanceRequestByIdShouldReturn200() throws Exception {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setProperty(testProperty);
        request.setTenant(testTenant);
        request.setDescription("Fix broken window");
        request.setStatus(MaintenanceRequestStatus.IN_PROGRESS);
        request.setCost(new BigDecimal("150.00"));
        request.setDateCreated(LocalDateTime.now());
        MaintenanceRequest saved = maintenanceRequestRepository.save(request);

        mockMvc.perform(get("/api/maintenance-requests/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Fix broken window")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void updateMaintenanceRequestShouldReturn200() throws Exception {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setProperty(testProperty);
        request.setTenant(testTenant);
        request.setDescription("Fix broken window");
        request.setStatus(MaintenanceRequestStatus.IN_PROGRESS);
        request.setCost(new BigDecimal("150.00"));
        request.setDateCreated(LocalDateTime.now());
        MaintenanceRequest saved = maintenanceRequestRepository.save(request);

        MaintenanceRequestDto updated = new MaintenanceRequestDto();
        updated.setDescription("Fix broken window - urgent");
        updated.setStatus(MaintenanceRequestStatus.IN_PROGRESS);
        updated.setCost(new BigDecimal("200.00"));

        mockMvc.perform(put("/api/maintenance-requests/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Fix broken window - urgent")))
                .andExpect(jsonPath("$.status", is("IN_PROGRESS")))
                .andExpect(jsonPath("$.cost", is(200.00)));
    }

    @Test
    void deleteMaintenanceRequestShouldReturn204() throws Exception {
        MaintenanceRequest request = new MaintenanceRequest();
        request.setProperty(testProperty);
        request.setTenant(testTenant);
        request.setDescription("Fix broken window");
        request.setStatus(MaintenanceRequestStatus.IN_PROGRESS);
        request.setDateCreated(LocalDateTime.now());
        MaintenanceRequest saved = maintenanceRequestRepository.save(request);

        mockMvc.perform(delete("/api/maintenance-requests/" + saved.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, maintenanceRequestRepository.count());
    }
}
