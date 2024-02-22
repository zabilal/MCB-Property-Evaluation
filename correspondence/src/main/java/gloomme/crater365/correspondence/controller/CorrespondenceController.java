package gloomme.crater365.correspondence.controller;

import gloomme.crater365.correspondence.dto.CorrespondenceDTO;
import gloomme.crater365.correspondence.dto.ReviewDTO;
import gloomme.crater365.correspondence.service.CorrespondenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/correspondence")
@AllArgsConstructor
public class CorrespondenceController {

    private CorrespondenceService correspondenceService;

    @PostMapping
    public ResponseEntity<?> createCorrespondence(@RequestBody CorrespondenceDTO correspondenceDTO) {
        return new ResponseEntity<>(correspondenceService.createNewCorrespondence(correspondenceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{organizationCraterId}")
    public ResponseEntity<?> getAllOrganizationsCorrespondence(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @PathVariable(name = "organizationCraterId") String organizationCraterId) {
        return new ResponseEntity<>(correspondenceService.getAllByOrganizationsCraterId(page, size, organizationCraterId), HttpStatus.OK);
    }

    @GetMapping("/{organizationCraterId}/{craterId}")
    public ResponseEntity<?> getOrganizationCorrespondenceByCraterId(@PathVariable("organizationCraterId") String organizationCraterId,
                                                                     @PathVariable("craterId") String craterId) {
        return new ResponseEntity<>(correspondenceService.getOneCorrespondenceByOrganization(organizationCraterId, craterId), HttpStatus.OK);
    }

    @PostMapping("/workflow/process")
    public ResponseEntity<?> processCorrespondenceForOrganization(@RequestBody CorrespondenceDTO dto) {
        return new ResponseEntity<>(correspondenceService.processCorrespondence(dto), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateCorrespondenceForOrganization(@RequestBody CorrespondenceDTO dto) {
        return new ResponseEntity<>(correspondenceService.updateCorrespondence(dto), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO dto) {
        return new ResponseEntity<>(correspondenceService.addReviews(dto), HttpStatus.OK);
    }
}
