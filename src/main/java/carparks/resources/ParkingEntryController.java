package carparks.resources;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import carparks.parking.DailySummaryDto;
import carparks.parking.ParkingService;

@RestController
class ParkingEntryController {
	
//	private List<CurrencyConverter> converters;
	private ParkingService service;
	
	@Autowired
	public ParkingEntryController(ParkingService service) {
		this.service = service;
	}
	
	// -------- More endpoints provided by Spring Data REST --------- //
	
	// --------------- Retrieve a parking entry --------------------- //
	@GetMapping(value = "/{plateNumber}/{entryId}")
	public ResponseEntity<?> getParking(@PathVariable("plateNumber") String plateNumber,
										@PathVariable("entryId") long id) {
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	// --------------- Create a parking entry ---------------------- //
	@PostMapping(value = "/{plateNumber}/parking")
	public ResponseEntity<?> createParking( @PathVariable("plateNumber") String plateNumber,
											@RequestBody String type) {
//		HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/fee/{id}").buildAndExpand(fee.getId()).toUri());
//        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// -------------- Update a parking entry ------------------------ //
	@PutMapping(value = "/{plateNumber}/{entryId}")
	public ResponseEntity<?> updateParking(@PathVariable("plateNumber") String plateNumber,
										   @PathVariable("id") long id) {
		return new ResponseEntity<String>("new fee", HttpStatus.CREATED);
	}
	
	// ---- Retrieve the summary of all fees in a given day ---- //
	@GetMapping(value = "/summary/{date}")
	public ResponseEntity<?> getFeesSumOnAGivenDay(@PathVariable(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		DailySummaryDto summary = service.getDailySummary(date);
		return new ResponseEntity<DailySummaryDto>(summary, HttpStatus.OK);
	}
	
	// ---- Retrieve the summary of all fees today ---- //
	@GetMapping(value = "/summary")
	public ResponseEntity<?> getFeesSumToday() {	
		DailySummaryDto summary = service.getDailySummary(LocalDate.now());
		return new ResponseEntity<DailySummaryDto>(summary, HttpStatus.OK);
	}
	
	

}
