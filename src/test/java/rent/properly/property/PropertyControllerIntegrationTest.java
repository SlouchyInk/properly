package rent.properly.property;

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
public class PropertyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        propertyRepository.deleteAll();
    }

    @Test
    void createPropertyShouldReturn201AndSavetoDatabase() throws Exception {
        PropertyDto propertyDto = new PropertyDto();
        propertyDto.setAddressLine1("123 Main Street");
        propertyDto.setAddressLine2("Unit 4");
        propertyDto.setCity("Springfield");
        propertyDto.setState("IL");
        propertyDto.setZipcode("62701");

        mockMvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(propertyDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.addressLine1", is("123 Main Street")))
                .andExpect(jsonPath("$.addressLine2", is("Unit 4")))
                .andExpect(jsonPath("$.city", is("Springfield")))
                .andExpect(jsonPath("$.state", is("IL")))
                .andExpect(jsonPath("$.zipcode", is("62701")))
                .andExpect(jsonPath("$.id").exists());

        assert propertyRepository.count() == 1;
    }

    @Test
    void getPropertyByIdShouldReturn200AndProperty() throws Exception {
        Property property = new Property();
        property.setAddressLine1("123 Main Street");
        property.setAddressLine2("Unit 4");
        property.setCity("Springfield");
        property.setState("IL");
        property.setZipcode("62701");
        Property saved = propertyRepository.save(property);

        mockMvc.perform(get("/api/properties/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressLine1", is("123 Main Street")))
                .andExpect(jsonPath("$.addressLine2", is("Unit 4")))
                .andExpect(jsonPath("$.city", is("Springfield")))
                .andExpect(jsonPath("$.state", is("IL")))
                .andExpect(jsonPath("$.zipcode", is("62701")))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void updatePropertyShouldReturn200AndUpdatedProperty() throws Exception {
        Property property = new Property();
        property.setAddressLine1("123 Main Street");
        property.setAddressLine2("Unit 4");
        property.setCity("Springfield");
        property.setState("IL");
        property.setZipcode("62701");
        Property saved = propertyRepository.save(property);

        PropertyDto updated = new PropertyDto();
        updated.setAddressLine1("456 Main Street");
        updated.setAddressLine2("Unit 4");
        updated.setCity("Springfield");
        updated.setState("IL");
        updated.setZipcode("62701");

        mockMvc.perform(put("/api/properties/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressLine1", is("456 Main Street")))
                .andExpect(jsonPath("$.addressLine2", is("Unit 4")))
                .andExpect(jsonPath("$.city", is("Springfield")))
                .andExpect(jsonPath("$.state", is("IL")))
                .andExpect(jsonPath("$.zipcode", is("62701")));

        assertEquals("456 Main Street", updated.getAddressLine1());
        assertEquals("Unit 4", updated.getAddressLine2());
        assertEquals("Springfield", updated.getCity());
        assertEquals("IL", updated.getState());
        assertEquals("62701", updated.getZipcode());
    }

    @Test
    void deletePropertyShouldReturn204AndDeleteFromDatabase() throws Exception {
        Property property = new Property();
        property.setAddressLine1("123 Main Street");
        property.setAddressLine2("Unit 4");
        property.setCity("Springfield");
        property.setState("IL");
        property.setZipcode("62701");
        Property saved = propertyRepository.save(property);

        mockMvc.perform(delete("/api/properties/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertEquals(0, propertyRepository.count());
        assert !propertyRepository.existsById(saved.getId());
    }
}
