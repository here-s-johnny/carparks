package carparks.resources;

import java.time.LocalDate;

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
import carparks.parking.FeeType;
import carparks.parking.ParkingEntryDto;
import carparks.parking.ParkingService;

@RestController
class ParkingEntryController {
	
	private ParkingService service;
	
	@Autowired
	public ParkingEntryController(ParkingService service) {
		this.service = service;
	}
	
	// -------- More endpoints provided by Spring Data REST --------- //
	
	// ---------- Retrieve a parking entry by plate number ----------- //
	@GetMapping(value = "/platenumber/{plateNumber}")
	public ResponseEntity<?> getParkingEntryByPlateNumber(@PathVariable("plateNumber") String plateNumber,
			@RequestParam(value = "currency", required = false, defaultValue = "PLN") String currency) {
		
		ParkingEntryDto entry = service.getEntry(plateNumber, currency);
		
		return new ResponseEntity<ParkingEntryDto>(entry, HttpStatus.OK);
	}
	
	// ------------ Retrieve a parking entry by entry id ------------ //
	@GetMapping(value = "/id/{entryId}")
	public ResponseEntity<?> getParkingEntryByEntryId(@PathVariable("entryId") int id,
			@RequestParam(value = "currency", required = false, defaultValue = "PLN") String currency) {
		
		ParkingEntryDto entry = service.getEntry(id, currency);
		
		return new ResponseEntity<ParkingEntryDto>(entry, HttpStatus.OK);
	}
	
	// --------------- Create a parking entry ---------------------- //
	@PostMapping(value = "/{plateNumber}/parking")
	public ResponseEntity<?> createParkingEntry(@PathVariable("plateNumber") String plateNumber,
											@RequestBody FeeType type) {
		
		ParkingEntryDto entry = service.tryToCreateParkingEntry(plateNumber, type);
		
		return new ResponseEntity<ParkingEntryDto>(entry, HttpStatus.CREATED);
	}
	
	// ---------- Finish parking session ----------------- //
	@PutMapping(value = "/{plateNumber}")
	public ResponseEntity<?> finishParking(@PathVariable("plateNumber") String plateNumber) {
		
		ParkingEntryDto entry = service.tryToFinishParkingSession(plateNumber);
		
		return new ResponseEntity<ParkingEntryDto>(entry, HttpStatus.CREATED);
	}
	
	// ---- Retrieve the summary of all fees in a given day ---- //
	@GetMapping(value = "/summary/{date}")
	public ResponseEntity<?> getFeesSumOnAGivenDay(@PathVariable(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
													@RequestParam(value = "currency", required = false, defaultValue = "PLN") String currency) {
		DailySummaryDto summary = service.getDailySummary(date, currency);
		return new ResponseEntity<DailySummaryDto>(summary, HttpStatus.OK);
	}
	
	// ---- Retrieve the summary of all fees today ---- //
	@GetMapping(value = "/summary")
	public ResponseEntity<?> getFeesSumToday(@RequestParam(value = "currency", required = false, defaultValue = "PLN") String currency) {	
		DailySummaryDto summary = service.getDailySummary(LocalDate.now(), currency);
		return new ResponseEntity<DailySummaryDto>(summary, HttpStatus.OK);
	}
	
	

}
