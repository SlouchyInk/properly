package rent.properly.lease;

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
import rent.properly.property.Property;
import rent.properly.property.PropertyDto;
import rent.properly.property.PropertyRepository;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public class LeaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeaseRepository leaseRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Property savedProperty;

    @BeforeEach
    void setUp() {
        leaseRepository.deleteAll();
        propertyRepository.deleteAll();

        Property property = new Property();
        property.setAddressLine1("123 Main Street");
        property.setAddressLine2("Unit 1");
        property.setCity("Springfield");
        property.setState("IL");
        property.setZipcode("62701");
        savedProperty = propertyRepository.save(property);
    }

    @Test
    void createLeaseShouldReturn201AndSaveToDatabase() throws Exception {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setId(savedProperty.getId());

        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setProperty(propertyDto);
        leaseDto.setMoveInDate(LocalDate.of(2025, 1, 1));
        leaseDto.setMoveOutDate(LocalDate.of(2026, 1, 1));
        leaseDto.setRentAmount(new BigDecimal("1500.00"));
        leaseDto.setStatus(LeaseStatus.ACTIVE);
        leaseDto.setRentDueDate(LocalDate.of(2025, 2, 1));
        leaseDto.setSecurityDeposit(new BigDecimal("3000.00"));

        mockMvc.perform(post("/api/leases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(leaseDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.moveInDate", is("2025-01-01")))
                .andExpect(jsonPath("$.moveOutDate", is("2026-01-01")))
                .andExpect(jsonPath("$.rentAmount", is(1500.00)))
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.rentDueDate", is("2025-02-01")))
                .andExpect(jsonPath("$.securityDeposit", is(3000.00)))
                .andExpect(jsonPath("$.id").exists());

        assert leaseRepository.count() == 1;
    }

    @Test
    void getLeaseByIdShouldReturn200AndLease() throws Exception {
        Lease lease = new Lease();
        lease.setProperty(savedProperty);
        lease.setMoveInDate(LocalDate.of(2025, 1, 1));
        lease.setMoveOutDate(LocalDate.of(2026, 1, 1));
        lease.setRentAmount(new BigDecimal("1500.00"));
        lease.setStatus(LeaseStatus.ACTIVE);
        lease.setRentDueDate(LocalDate.of(2025, 2, 1));
        lease.setSecurityDeposit(new BigDecimal("3000.00"));
        Lease saved = leaseRepository.save(lease);

        mockMvc.perform(get("/api/leases/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moveInDate", is("2025-01-01")))
                .andExpect(jsonPath("$.moveOutDate", is("2026-01-01")))
                .andExpect(jsonPath("$.rentAmount", is(1500.00)))
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.rentDueDate", is("2025-02-01")))
                .andExpect(jsonPath("$.securityDeposit", is(3000.00)))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void updateLeaseShouldReturn200AndUpdatedLease() throws Exception {
        Lease lease = new Lease();
        lease.setProperty(savedProperty);
        lease.setMoveInDate(LocalDate.of(2025, 1, 1));
        lease.setMoveOutDate(LocalDate.of(2026, 1, 1));
        lease.setRentAmount(new BigDecimal("1500.00"));
        lease.setStatus(LeaseStatus.ACTIVE);
        lease.setRentDueDate(LocalDate.of(2025, 2, 1));
        lease.setSecurityDeposit(new BigDecimal("3000.00"));
        Lease saved = leaseRepository.save(lease);

        LeaseDto updated = new LeaseDto();
        updated.setMoveInDate(LocalDate.of(2025, 6, 1));
        updated.setMoveOutDate(LocalDate.of(2026, 6, 1));
        updated.setRentAmount(new BigDecimal("1800.00"));
        updated.setStatus(LeaseStatus.PENDING);
        updated.setRentDueDate(LocalDate.of(2025, 7, 1));
        updated.setSecurityDeposit(new BigDecimal("3600.00"));

        mockMvc.perform(put("/api/leases/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moveInDate", is("2025-06-01")))
                .andExpect(jsonPath("$.moveOutDate", is("2026-06-01")))
                .andExpect(jsonPath("$.rentAmount", is(1800.00)))
                .andExpect(jsonPath("$.status", is("PENDING")))
                .andExpect(jsonPath("$.rentDueDate", is("2025-07-01")))
                .andExpect(jsonPath("$.securityDeposit", is(3600.00)));

        assertEquals(LocalDate.of(2025, 6, 1), updated.getMoveInDate());
        assertEquals(LocalDate.of(2026, 6, 1), updated.getMoveOutDate());
        assertEquals(new BigDecimal("1800.00"), updated.getRentAmount());
        assertEquals(LeaseStatus.PENDING, updated.getStatus());
        assertEquals(LocalDate.of(2025, 7, 1), updated.getRentDueDate());
        assertEquals(new BigDecimal("3600.00"), updated.getSecurityDeposit());
    }

    @Test
    void deleteLeaseShouldReturn204AndDeleteFromDatabase() throws Exception {
        Lease lease = new Lease();
        lease.setProperty(savedProperty);
        lease.setMoveInDate(LocalDate.of(2025, 1, 1));
        lease.setMoveOutDate(LocalDate.of(2026, 1, 1));
        lease.setRentAmount(new BigDecimal("1500.00"));
        lease.setStatus(LeaseStatus.ACTIVE);
        lease.setRentDueDate(LocalDate.of(2025, 2, 1));
        lease.setSecurityDeposit(new BigDecimal("3000.00"));
        Lease saved = leaseRepository.save(lease);

        mockMvc.perform(delete("/api/leases/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertEquals(0, leaseRepository.count());
        assert !leaseRepository.existsById(saved.getId());
    }
}