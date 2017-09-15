package carparks.resources;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ParkingEntryController {
	
	// -------- More endpoints provided by Spring Data REST --------- //
	
	// --------------- Retrieve a parking record --------------------- //
	@GetMapping(value = "/{plateNumber}/{entryId}")
	public ResponseEntity<?> getParking(@PathVariable("plateNumber") String plateNumber,
										@PathVariable("entryId") long id) {
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	// --------------- Create a parking entity ---------------------- //
	@PostMapping(value = "/{plateNumber}/parking")
	public ResponseEntity<?> createParking( @PathVariable("plateNumber") String plateNumber,
											@RequestBody String type) {
//		HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/fee/{id}").buildAndExpand(fee.getId()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// -------------- Update a parking entity ------------------------ //
	@PutMapping(value = "/{plateNumber}/{entryId}")
	public ResponseEntity<?> updateParking(@PathVariable("plateNumber") String plateNumber,
										   @PathVariable("id") long id) {
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// ---- Retrieve the summary of all fees in a given day ---- //
	@GetMapping(value = "/summary")
	public ResponseEntity<?> getFeesSum(@RequestBody LocalDate date) {
		return new ResponseEntity<String>("sum", HttpStatus.OK);
	}
	
	

}
