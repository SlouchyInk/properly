package rent.properly.landlord;

public interface LandlordService {
    LandlordDto createLandlord(LandlordDto landlordDto);
    LandlordDto getLandlordById(Long id);
    LandlordDto updateLandlord(Long id, LandlordDto landlordDto);
    void deleteLandlord(Long id);
}
