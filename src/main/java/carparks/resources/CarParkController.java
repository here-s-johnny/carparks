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
class FeesController {
	
	// --------------- Retrieve a parking record --------------------- //
	@GetMapping(value = "/parking/{id}")
	public ResponseEntity<?> getParking(@PathVariable("id") long id) {
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	// --------------- Create a parking entity ---------------------- //
	@PostMapping(value = "/parking")
	public ResponseEntity<?> createParking(@RequestBody String plateNumber) {
//		HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/fee/{id}").buildAndExpand(fee.getId()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// -------------- Update a parking entity ------------------------ //
	@PutMapping(value = "/parking/{id}")
	public ResponseEntity<?> updateParking(@PathVariable("id") long id) {
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// ---- Retrieve the sum of all fees in a given day ---- //
	@GetMapping(value = "/fees")
	public ResponseEntity<?> getFeesSum(@RequestBody LocalDate date) {
		return new ResponseEntity<String>("sum", HttpStatus.OK);
	}
	
	

}
