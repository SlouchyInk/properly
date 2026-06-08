package rent.properly.lease;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class LeaseServiceImpl implements LeaseService{
    private final LeaseRepository leaseRepository;
    
    private final LeaseMapper leaseMapper;
    
    public LeaseServiceImpl(LeaseRepository leaseRepository, LeaseMapper leaseMapper) {
        this.leaseRepository = leaseRepository;
        this.leaseMapper = leaseMapper;
    }
    
    @Override
    public LeaseDto createLease(LeaseDto leaseDto) {
        Lease lease = leaseRepository.save(leaseMapper.toEntity(leaseDto));
        return leaseMapper.toDto(lease);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaseDto getLeaseById(Long id) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease", id));
        return leaseMapper.toDto(lease);
    }

    @Override
    @Transactional(readOnly = true)
    public LeaseDto updateLease(Long id, LeaseDto updatedLease) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease", id));
        lease.setMoveInDate(updatedLease.getMoveInDate());
        lease.setMoveOutDate(updatedLease.getMoveOutDate());
        lease.setRentAmount(updatedLease.getRentAmount());
        lease.setStatus(updatedLease.getStatus());
        lease.setRentDueDate(updatedLease.getRentDueDate());
        lease.setSecurityDeposit(updatedLease.getSecurityDeposit());
        Lease updatedLeaseEntity = leaseRepository.save(lease);
        return leaseMapper.toDto(updatedLeaseEntity);
    }

    @Override
    public void deleteLease(Long id) {
        Lease lease = leaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lease", id));
        leaseRepository.delete(lease);
    }
}
