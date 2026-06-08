package rent.properly.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentService = new PaymentServiceImpl(paymentRepository, paymentMapper);
    }

    @Test
    void createPaymentShouldCallRepositorySave() {
        PaymentDto paymentDto = new PaymentDto();
        Payment payment = new Payment();
        Payment savedPayment = new Payment();
        PaymentDto savedPaymentDto = new PaymentDto();

        when(paymentMapper.toEntity(paymentDto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(savedPayment);
        when(paymentMapper.toDto(savedPayment)).thenReturn(savedPaymentDto);

        PaymentDto result = paymentService.createPayment(paymentDto);

        assertSame(savedPaymentDto, result);
        verify(paymentMapper).toEntity(paymentDto);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).toDto(savedPayment);
    }

    @Test
    void getPaymentByIdShouldCallRepositoryFindById() {
        Long paymentId = 1L;
        Payment payment = new Payment();
        PaymentDto paymentDto = new PaymentDto();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);

        PaymentDto result = paymentService.getPaymentById(paymentId);

        assertSame(paymentDto, result);
        verify(paymentRepository).findById(paymentId);
        verify(paymentMapper).toDto(payment);
    }

    @Test
    void updatePaymentShouldCallRepositorySave() {
        Long paymentId = 1L;
        Payment existingPayment = new Payment();
        Payment savedPayment = new Payment();
        PaymentDto updatedPaymentDto = new PaymentDto();
        PaymentDto savedPaymentDto = new PaymentDto();

        updatedPaymentDto.setPaymentAmount(BigDecimal.valueOf(1500.00));
        updatedPaymentDto.setPaymentDate(LocalDateTime.of(2026, 6, 1, 12, 0));
        updatedPaymentDto.setStatus(PaymentStatus.COMPLETED);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(paymentRepository.save(existingPayment)).thenReturn(savedPayment);
        when(paymentMapper.toDto(savedPayment)).thenReturn(savedPaymentDto);

        PaymentDto result = paymentService.updatePayment(paymentId, updatedPaymentDto);

        assertSame(savedPaymentDto, result);
        verify(paymentRepository).findById(paymentId);
        verify(paymentRepository).save(existingPayment);
        verify(paymentMapper).toDto(savedPayment);
    }

    @Test
    void deletePaymentShouldCallRepositoryDelete() {
        Long paymentId = 1L;
        Payment payment = new Payment();

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(payment));

        paymentService.deletePayment(paymentId);

        verify(paymentRepository).findById(paymentId);
        verify(paymentRepository).delete(payment);
    }
}