package rent.properly.landlord;

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
public class LandlordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        landlordRepository.deleteAll();
    }

    @Test
    void createLandlordShouldReturn201AndSaveToDatabase() throws Exception {
        LandlordDto landlordDto = new LandlordDto();
        landlordDto.setFirstName("John");
        landlordDto.setMiddleName("A");
        landlordDto.setLastName("Doe");
        landlordDto.setEmail("john.doe@example.com");
        landlordDto.setPhoneNumber("555-1234");

        mockMvc.perform(post("/api/landlords")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landlordDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.middleName", is("A")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234")))
                .andExpect(jsonPath("$.id").exists());

        assert landlordRepository.count() == 1;
    }

    @Test
    void getLandlordByIdShouldReturn200AndLandlord() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("John");
        landlord.setMiddleName("A");
        landlord.setLastName("Doe");
        landlord.setEmail("john.doe@example.com");
        landlord.setPhoneNumber("555-1234");
        Landlord saved = landlordRepository.save(landlord);

        mockMvc.perform(get("/api/landlords/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.middleName", is("A")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234")))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void updateLandlordShouldReturn200AndUpdatedLandlord() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("John");
        landlord.setMiddleName("A");
        landlord.setLastName("Doe");
        landlord.setEmail("john.doe@example.com");
        landlord.setPhoneNumber("555-1234");
        Landlord saved = landlordRepository.save(landlord);

        LandlordDto updated = new LandlordDto();
        updated.setFirstName("Jane");
        updated.setMiddleName("B");
        updated.setLastName("Smith");
        updated.setEmail("jane.smith@example.com");
        updated.setPhoneNumber("555-5678");

        mockMvc.perform(put("/api/landlords/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Jane")))
                .andExpect(jsonPath("$.middleName", is("B")))
                .andExpect(jsonPath("$.lastName", is("Smith")))
                .andExpect(jsonPath("$.email", is("jane.smith@example.com")))
                .andExpect(jsonPath("$.phoneNumber", is("555-5678")));

        assertEquals("Jane", updated.getFirstName());
        assertEquals("B", updated.getMiddleName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("jane.smith@example.com", updated.getEmail());
        assertEquals("555-5678", updated.getPhoneNumber());
    }

    @Test
    void deleteLandlordShouldReturn204AndDeleteFromDatabase() throws Exception {
        Landlord landlord = new Landlord();
        landlord.setFirstName("John");
        landlord.setMiddleName("A");
        landlord.setLastName("Doe");
        landlord.setEmail("john.doe@example.com");
        landlord.setPhoneNumber("555-1234");
        Landlord saved = landlordRepository.save(landlord);

        mockMvc.perform(delete("/api/landlords/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertEquals(0, landlordRepository.count());
        assert !landlordRepository.existsById(saved.getId());
    }
}