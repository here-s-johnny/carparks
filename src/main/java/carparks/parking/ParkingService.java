package carparks.parking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

	private ParkingDao parkingDao;
	private FeeCalculator feeCalculator;
	private List<CurrencyConverter> converters;

	@Autowired
	public ParkingService(ParkingDao parkingDao, FeeCalculator feeCalculator, List<CurrencyConverter> converters) {
		this.parkingDao = parkingDao;
		this.feeCalculator = feeCalculator;
	}

	public ParkingEntryDto tryToCreateParkingEntry(String plateNumber, FeeType feeType) {

		List<ParkingEntry> entries = getStartedSessionsListByPlateNumber(plateNumber);

		if (entries.size() > 0) {

			throw new IllegalStateException("This vehicle has already started a parking session");

		} else {

			ParkingEntry newEntry = new ParkingEntry(plateNumber, feeType);
			parkingDao.save(newEntry);
			return new ParkingEntryDto(plateNumber, new BigDecimal(0), SessionStatus.IN_PROGRESS);
		}
	}

	public ParkingEntryDto tryToFinishParkingSession(String plateNumber) {

		List<ParkingEntry> entries = getStartedSessionsListByPlateNumber(plateNumber);

		if (entries.size() == 0) {

			throw new IllegalStateException("This vehicle hasn not started a parking session");

		} else {

			ParkingEntry updatedEntry = entries.get(0);
			updatedEntry.setFinish(LocalDateTime.now());
			updatedEntry.setFee(feeCalculator.calculateFee(updatedEntry));
			parkingDao.save(updatedEntry);
			return new ParkingEntryDto(plateNumber, new BigDecimal(1), SessionStatus.FINISHED);

		}

	}

	public Optional<LocalDateTime> checkIfVehicleStartedParkingSession(String plateNumber) {

		List<ParkingEntry> entries = getStartedSessionsListByPlateNumber(plateNumber);

		if (entries.size() > 0) {
			return Optional.ofNullable(entries.get(0).getStart());
		} else {
			return Optional.empty();
		}
	}

	public ParkingEntryDto getEntry(String plateNumber, String currency){

		List<ParkingEntry> entriesStarted = getStartedSessionsListByPlateNumber(plateNumber);

		if (entriesStarted.size() > 0) {
			BigDecimal feeSoFar = feeCalculator.calculateFee(entriesStarted.get(0));
			
			if (currency != "PLN") {
				feeSoFar = getConvertedValue(feeSoFar, currency);
			}
			
			return new ParkingEntryDto(plateNumber, feeSoFar, SessionStatus.IN_PROGRESS);
		}
		
		ParkingEntry mostRecent = getMostRecentFinishedSession(plateNumber);
		BigDecimal fee = mostRecent.getFee();
		if (currency != "PLN") {
			fee = getConvertedValue(fee, currency);
		}
		return new ParkingEntryDto(plateNumber, fee, SessionStatus.FINISHED);

	}

	public ParkingEntryDto getEntry(int id, String currency) {

		ParkingEntry entry = parkingDao.findById(id);

		if (entry != null) {
			if (entry.getFinish() == null) {
				BigDecimal feeSoFar = feeCalculator.calculateFee(entry);
				
				if (currency != "PLN") {
					feeSoFar = getConvertedValue(feeSoFar, currency);
				}
				
				return new ParkingEntryDto(entry.getPlateNumber(), feeSoFar, SessionStatus.IN_PROGRESS);
			}
			
			BigDecimal fee = entry.getFee();
			if (currency != "PLN") {
				fee = getConvertedValue(fee, currency);
			}
			return new ParkingEntryDto(entry.getPlateNumber(), fee, SessionStatus.FINISHED);
		}
		
		throw new IllegalStateException("This vehicle has never parked");
	}
	
	public DailySummaryDto getDailySummary(String currency) {
		
		LocalDateTime start = LocalDate.now().atStartOfDay();
		LocalDateTime end = LocalDate.now().atTime(LocalTime.MAX);
		
		List<ParkingEntry> regularEntries = parkingDao.findByFeeTypeAndFinishBetween(FeeType.REGULAR, start, end);
		List<ParkingEntry> vipEntries = parkingDao.findByFeeTypeAndFinishBetween(FeeType.VIP, start, end);
		
		BigDecimal regularTurnover = feeCalculator.calculateDailyTurnover(regularEntries);
		BigDecimal vipTurnover = feeCalculator.calculateDailyTurnover(vipEntries);
		
		if (currency != "PLN") {
			regularTurnover = getConvertedValue(regularTurnover, currency);
			vipTurnover = getConvertedValue(vipTurnover, currency);
		}
		
		return new DailySummaryDto(regularTurnover, vipTurnover);
		
	}
	
	
	public DailySummaryDto getDailySummary(LocalDate date, String currency) {
		
		if (date.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("The given day is in the future");
		}
		
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		
		List<ParkingEntry> regularEntries = parkingDao.findByFeeTypeAndFinishBetween(FeeType.REGULAR, start, end);
		List<ParkingEntry> vipEntries = parkingDao.findByFeeTypeAndFinishBetween(FeeType.VIP, start, end);
		
		BigDecimal regularTurnover = feeCalculator.calculateDailyTurnover(regularEntries);
		BigDecimal vipTurnover = feeCalculator.calculateDailyTurnover(vipEntries);
		
		if (currency != "PLN") {
			regularTurnover = getConvertedValue(regularTurnover, currency);
			vipTurnover = getConvertedValue(vipTurnover, currency);
		}
		
		return new DailySummaryDto(regularTurnover, vipTurnover);
		
	}

	private List<ParkingEntry> getStartedSessionsListByPlateNumber(String plateNumber) {

		List<ParkingEntry> entries = parkingDao.findByPlateNumber(plateNumber);

		return entries.stream().filter(e -> (e.getFinish() == null))
				.collect(Collectors.toList());
	}

	private List<ParkingEntry> getFinishedSessionsListByPlateNumber(String plateNumber) {

		List<ParkingEntry> entries = parkingDao.findByPlateNumber(plateNumber);

		return entries.stream().filter(ParkingEntry::isFinished)
				.collect(Collectors.toList());
	}
	
	private ParkingEntry getMostRecentFinishedSession(String plateNumber) {
		return getFinishedSessionsListByPlateNumber(plateNumber).
				stream().
				max(Comparator.comparing(e -> e.getFinish())).
				orElseThrow(() -> new IllegalStateException("This vehicle has never parked"));
	}
	
	private BigDecimal getConvertedValue(BigDecimal fee, String currency) {

		Optional<CurrencyConverter> converter = converters.stream().filter(c -> c.supports(currency)).findFirst();
		if (!converter.isPresent()) {
			throw new IllegalArgumentException("This currency is not supported yet");
		} else {
			return converter.get().convert(fee);			}
	}

}
