package rent.properly.lease;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LeaseServiceTest {

    @Mock
    private LeaseRepository leaseRepository;
    @Mock
    private LeaseMapper leaseMapper;

    private LeaseService leaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        leaseService = new LeaseServiceImpl(leaseRepository, leaseMapper);
    }

    @Test
    void createLeaseShouldCallRepositorySave() {
        LeaseDto leaseDto = new LeaseDto();
        Lease lease = new Lease();
        Lease savedLease = new Lease();
        LeaseDto savedLeaseDto = new LeaseDto();

        when(leaseMapper.toEntity(leaseDto)).thenReturn(lease);
        when(leaseRepository.save(lease)).thenReturn(savedLease);
        when(leaseMapper.toDto(savedLease)).thenReturn(savedLeaseDto);

        LeaseDto result = leaseService.createLease(leaseDto);

        assertSame(savedLeaseDto, result);
        verify(leaseMapper).toEntity(leaseDto);
        verify(leaseRepository).save(lease);
        verify(leaseMapper).toDto(savedLease);
    }

    @Test
    void getLeaseByIdShouldCallRepositoryFindById() {
        Long leaseId = 1L;
        Lease lease = new Lease();
        LeaseDto leaseDto = new LeaseDto();

        when(leaseRepository.findById(leaseId)).thenReturn(Optional.of(lease));
        when(leaseMapper.toDto(lease)).thenReturn(leaseDto);

        LeaseDto result = leaseService.getLeaseById(leaseId);

        assertSame(leaseDto, result);
        verify(leaseRepository).findById(leaseId);
        verify(leaseMapper).toDto(lease);
    }

    @Test
    void updateLeaseShouldCallRepositorySave() {
        Long leaseId = 1L;
        Lease existingLease = new Lease();
        Lease savedLease = new Lease();
        LeaseDto updatedLeaseDto = new LeaseDto();
        LeaseDto savedLeaseDto = new LeaseDto();

        updatedLeaseDto.setMoveInDate(LocalDate.of(2025, 1, 1));
        updatedLeaseDto.setMoveOutDate(LocalDate.of(2026, 1, 1));
        updatedLeaseDto.setRentAmount(new BigDecimal("1500.00"));
        updatedLeaseDto.setStatus(LeaseStatus.ACTIVE);
        updatedLeaseDto.setRentDueDate(LocalDate.of(2025, 2, 1));
        updatedLeaseDto.setSecurityDeposit(new BigDecimal("3000.00"));

        when(leaseRepository.findById(leaseId)).thenReturn(Optional.of(existingLease));
        when(leaseRepository.save(existingLease)).thenReturn(savedLease);
        when(leaseMapper.toDto(savedLease)).thenReturn(savedLeaseDto);

        LeaseDto result = leaseService.updateLease(leaseId, updatedLeaseDto);

        assertSame(savedLeaseDto, result);
        verify(leaseRepository).findById(leaseId);
        verify(leaseRepository).save(existingLease);
        verify(leaseMapper).toDto(savedLease);
    }

    @Test
    void deleteLeaseShouldCallRepositoryDelete() {
        Long leaseId = 1L;
        Lease lease = new Lease();

        when(leaseRepository.findById(leaseId)).thenReturn(Optional.of(lease));

        leaseService.deleteLease(leaseId);

        verify(leaseRepository).findById(leaseId);
        verify(leaseRepository).delete(lease);
    }
}