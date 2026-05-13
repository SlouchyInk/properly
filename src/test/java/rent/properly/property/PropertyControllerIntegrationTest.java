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
}
