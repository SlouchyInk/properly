package rent.properly.lease;

public interface LeaseService {
    LeaseDto createLease(LeaseDto leaseDto);
    LeaseDto getLeaseById(Long id);
    LeaseDto updateLease(Long id, LeaseDto leaseDto);
    void deleteLease(Long id);
}
