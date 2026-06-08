package rent.properly.landlord;

import org.springframework.stereotype.Service;
import rent.properly.exception.ResourceNotFoundException;

@Service
public class LandlordServiceImpl implements LandlordService {
    private final LandlordRepository landlordRepository;

    private final LandlordMapper landlordMapper;

    public LandlordServiceImpl(LandlordRepository landlordRepository, LandlordMapper landlordMapper) {
        this.landlordRepository = landlordRepository;
        this.landlordMapper = landlordMapper;
    }

    @Override
    public LandlordDto createLandlord(LandlordDto landlordDto) {
        Landlord landlord = landlordRepository.save(landlordMapper.toEntity(landlordDto));
        return landlordMapper.toDto(landlord);
    }

    @Override
    public LandlordDto getLandlordById(Long id) {
        Landlord landlord = landlordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Landlord" + id + ""));
        return landlordMapper.toDto(landlord);
    }

    @Override
    public LandlordDto updateLandlord(Long id, LandlordDto updatedLandlord) {
        Landlord landlord = landlordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Landlord" + id + ""));
        landlord.setFirstName(updatedLandlord.getFirstName());
        landlord.setMiddleName(updatedLandlord.getMiddleName());
        landlord.setLastName(updatedLandlord.getLastName());
        landlord.setEmail(updatedLandlord.getEmail());
        landlord.setPhoneNumber(updatedLandlord.getPhoneNumber());

        Landlord updatedLandlordEntity = landlordRepository.save(landlord);
        return landlordMapper.toDto(updatedLandlordEntity);

    }

    @Override
    public void deleteLandlord(Long id) {
        Landlord landlord = landlordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Landlord" + id + ""));
        landlordRepository.delete(landlord);
    }
}
