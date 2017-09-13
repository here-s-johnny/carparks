package carparks.controllers;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FeesController {
	
	// --------------- Retrieve a fee --------------------- //
	@RequestMapping(value = "/fee/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getFee(@PathVariable("id") long id) {
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	// --------------- Create a fee ---------------------- //
	@RequestMapping(value = "/fee", method = RequestMethod.POST)
	public ResponseEntity<?> createFee(@RequestBody String plateNumber) {
//		HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/fee/{id}").buildAndExpand(fee.getId()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	
	// -------------- Update a fee ------------------------ //
	@RequestMapping(value = "/fee/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateFee(@PathVariable("id") long id) {
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// ---- Retrieve the sum of all fees in a given day ---- //
	@RequestMapping(value = "/fees", method = RequestMethod.GET)
	public ResponseEntity<?> getFeesSum(@RequestBody LocalDate date) {
		return new ResponseEntity<String>("sum", HttpStatus.OK);
	}
	
	

}
