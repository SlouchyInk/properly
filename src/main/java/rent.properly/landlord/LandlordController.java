package rent.properly.landlord;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/landlords")
public class LandlordController {
    private final LandlordService landlordService;

    public LandlordController(LandlordService landlordService) {
        this.landlordService = landlordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandlordDto> getLandlordById(@PathVariable Long id) {
        return ResponseEntity.ok(landlordService.getLandlordById(id));
    }

    @PostMapping
    public ResponseEntity<LandlordDto> createLandlord(@RequestBody LandlordDto landlordDto) {
        return ResponseEntity.created(null).body(landlordService.createLandlord(landlordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LandlordDto> updateLandlord(@PathVariable Long id, @RequestBody LandlordDto updatedLandlord) {
        return ResponseEntity.ok(landlordService.updateLandlord(id, updatedLandlord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLandlord(@PathVariable Long id) {
        landlordService.deleteLandlord(id);
        return ResponseEntity.noContent().build();
    }
}
