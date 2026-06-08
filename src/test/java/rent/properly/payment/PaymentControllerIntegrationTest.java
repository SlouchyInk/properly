package rent.properly.payment;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void createPaymentShouldReturn201AndSaveToDatabase() throws Exception {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentAmount(BigDecimal.valueOf(1200.00));
        paymentDto.setPaymentDate(LocalDateTime.of(2026, 6, 1, 10, 0));
        paymentDto.setStatus(PaymentStatus.COMPLETED);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentAmount", is(1200.00)))
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, paymentRepository.count());
    }

    @Test
    void getPaymentByIdShouldReturn200AndPayment() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentAmount(BigDecimal.valueOf(1200.00));
        payment.setPaymentDate(LocalDateTime.of(2026, 6, 1, 10, 0));
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment saved = paymentRepository.save(payment);

        mockMvc.perform(get("/api/payments/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentAmount", is(1200.00)))
                .andExpect(jsonPath("$.status", is("COMPLETED")))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())));
    }

    @Test
    void updatePaymentShouldReturn200AndUpdatedPayment() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentAmount(BigDecimal.valueOf(1200.00));
        payment.setPaymentDate(LocalDateTime.of(2026, 6, 1, 10, 0));
        payment.setStatus(PaymentStatus.PENDING);
        Payment saved = paymentRepository.save(payment);

        PaymentDto updated = new PaymentDto();
        updated.setPaymentAmount(BigDecimal.valueOf(1500.00));
        updated.setPaymentDate(LocalDateTime.of(2026, 7, 1, 10, 0));
        updated.setStatus(PaymentStatus.COMPLETED);

        mockMvc.perform(put("/api/payments/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentAmount", is(1500.00)))
                .andExpect(jsonPath("$.status", is("COMPLETED")));

        assertEquals(PaymentStatus.COMPLETED, updated.getStatus());
        assertEquals(BigDecimal.valueOf(1500.00), updated.getPaymentAmount());
    }

    @Test
    void deletePaymentShouldReturn204AndDeleteFromDatabase() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentAmount(BigDecimal.valueOf(1200.00));
        payment.setPaymentDate(LocalDateTime.of(2026, 6, 1, 10, 0));
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment saved = paymentRepository.save(payment);

        mockMvc.perform(delete("/api/payments/" + saved.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, paymentRepository.count());
        assert !paymentRepository.existsById(saved.getId());
    }
}