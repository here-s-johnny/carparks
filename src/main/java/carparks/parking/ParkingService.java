package carparks.parking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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

	@Autowired
	public ParkingService(ParkingDao parkingDao, FeeCalculator feeCalculator) {
		this.parkingDao = parkingDao;
		this.feeCalculator = feeCalculator;
	}

	public ParkingEntryDto tryToCreateParkingEntry(String plateNumber) {

		List<ParkingEntry> entries = getStartedSessionsListByPlateNumber(plateNumber);

		if (entries.size() > 0) {

			throw new IllegalStateException("This vehicle has already started a parking session");

		} else {

			ParkingEntry newEntry = new ParkingEntry(plateNumber);
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
			// tu jeszcze policzymy fee
			parkingDao.save(updatedEntry);
			return new ParkingEntryDto(plateNumber, new BigDecimal(1), SessionStatus.FINISHED);

		}

	}

	public Optional<LocalDateTime> checkIfVehicleStartedParkingSession(String plateNumber) {

		List<ParkingEntry> entries = getStartedSessionsListByPlateNumber(plateNumber);

		if (entries.size() > 0) {
			return Optional.ofNullable(entries.get(0).getStart());
		} else {
			return null;
		}
	}

	public ParkingEntryDto getFee(String plateNumber) {

		List<ParkingEntry> entriesStarted = getStartedSessionsListByPlateNumber(plateNumber);

		if (entriesStarted.size() > 0) {
			BigDecimal feeSoFar = feeCalculator.calculateFee(entriesStarted.get(0));
			return new ParkingEntryDto(plateNumber, feeSoFar, SessionStatus.IN_PROGRESS);
		}

		List<ParkingEntry> entriesFinished = getFinishedSessionsListByPlateNumber(plateNumber);

		if (entriesFinished.size() > 0) {
			ParkingEntry mostRecent = Collections.max(entriesFinished, Comparator.comparing(e -> e.getFinish()));
			return new ParkingEntryDto(plateNumber, mostRecent.getFee(), SessionStatus.FINISHED);
		}

		throw new IllegalStateException("This vehicle has never parked");

	}

	public ParkingEntryDto getFee(int id) {

		ParkingEntry entry = parkingDao.findById(id);

		if (entry != null) {
			if (entry.getFinish() == null) {
				BigDecimal feeSoFar = feeCalculator.calculateFee(entry);
				return new ParkingEntryDto(entry.getPlateNumber(), feeSoFar, SessionStatus.IN_PROGRESS);
			}
			return new ParkingEntryDto(entry.getPlateNumber(), entry.getFee(), SessionStatus.FINISHED);
		}
		
		
		throw new IllegalStateException("This vehicle has never parked");
	}
	
	public DailySummaryDto getDailySummary() {
		
		
		BigDecimal regularTurnover = feeCalculator.calculateDailyRegularTurnover(LocalDate.now());
		BigDecimal vipTurnover = feeCalculator.calculateDailyVipTurnover(LocalDate.now());
		
		return new DailySummaryDto(regularTurnover, vipTurnover);
		
	}
	//////////////////////DO POPRAWIENIA////////////////////////////////
	public DailySummaryDto getDailySummary(LocalDate date) {
		
		if (date.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("The given day is in the future");
		}
		
		BigDecimal regularTurnover = feeCalculator.calculateDailyRegularTurnover(date);
		BigDecimal vipTurnover = feeCalculator.calculateDailyVipTurnover(date);
		
		return new DailySummaryDto(regularTurnover, vipTurnover);
		
	}

	private List<ParkingEntry> getStartedSessionsListByPlateNumber(String plateNumber) {

		List<ParkingEntry> entries = parkingDao.findByPlateNumber(plateNumber);
		List<ParkingEntry> entriesFiltered = entries.stream().filter(e -> (e.getFinish() == null))
				.collect(Collectors.toList());

		return entriesFiltered;
	}

	private List<ParkingEntry> getFinishedSessionsListByPlateNumber(String plateNumber) {

		List<ParkingEntry> entries = parkingDao.findByPlateNumber(plateNumber);
		List<ParkingEntry> entriesFiltered = entries.stream().filter(e -> (e.getFinish() != null))
				.collect(Collectors.toList());

		return entriesFiltered;
	}

}
