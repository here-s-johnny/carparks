package carparks.parking;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import carparks.parking.ParkingDao;
import carparks.parking.ParkingService;

public class ParkingServiceShould {

	@Mock
	private ParkingDao parkingDao;

	@Mock
	private FeeCalculator feeCalculator;

	@InjectMocks
	private ParkingService parkingService = new ParkingService(parkingDao, feeCalculator);

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void create_new_parking_entry_successfully_when_no_other_session_in_progress() {

		// given
		String plate = "WF12345";

		// when
		ParkingEntryDto createdObject = parkingService.tryToCreateParkingEntry(plate);

		// then
		assertNotNull(createdObject);
		assertEquals(createdObject.getPlateNumber(), plate);

	}

	@Test(expected = IllegalStateException.class)
	public void throw_exception_when_trying_to_create_parking_entry_when_parking_session_already_started() {

		// given
		String plate = "WF12345";
		List<ParkingEntry> entries = Arrays.asList(new ParkingEntry(plate));
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		parkingService.tryToCreateParkingEntry(plate);

	}

	@Test
	public void create_parking_entry_when_previous_parking_session_already_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		entry.setFinish(LocalDateTime.now());
		List<ParkingEntry> entries = Arrays.asList(entry);
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		ParkingEntryDto createdObject = parkingService.tryToCreateParkingEntry(plate);

		// then
		assertNotNull(createdObject);
		assertEquals(createdObject.getPlateNumber(), plate);

	}

	@Test
	public void finish_parking_session_successfully() {

		// given
		String plate = "WF12345";
		List<ParkingEntry> entries = Arrays.asList(new ParkingEntry(plate));
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		ParkingEntryDto updatedObject = parkingService.tryToFinishParkingSession(plate);

		// then
		assertNotNull(updatedObject);
		assertEquals(updatedObject.getPlateNumber(), plate);
		assertTrue(updatedObject.getFee().compareTo(new BigDecimal(0)) > 0);

	}

	@Test(expected = IllegalStateException.class)
	public void throw_an_exception_when_trying_to_finish_parking_session_without_starting() {

		// given
		String plate = "WF12345";

		// when
		parkingService.tryToFinishParkingSession(plate);

	}

	@Test
	public void return_date_if_parking_session_has_started() {

		// given
		String plate = "WF12345";
		List<ParkingEntry> entries = Arrays.asList(new ParkingEntry(plate));
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		Optional<LocalDateTime> started = parkingService.checkIfVehicleStartedParkingSession(plate);

		// then
		assertNotNull(started);
	}

	@Test
	public void return_null_if_parking_has_not_started() {

		// given
		String plate = "WF12345";

		// when
		Optional<LocalDateTime> started = parkingService.checkIfVehicleStartedParkingSession(plate);

		// then
		assertNull(started);
	}

	@Test
	public void return_null_if_parking_started_and_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		entry.setFinish(LocalDateTime.now());
		List<ParkingEntry> entries = Arrays.asList(entry);
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		Optional<LocalDateTime> started = parkingService.checkIfVehicleStartedParkingSession(plate);

		// then
		assertNull(started);
	}

	@Test
	public void return_fee_when_checked_by_parking_entry_id_when_parking_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		entry.setFinish(LocalDateTime.now());
		entry.setFee(new BigDecimal(10));
		int id = 12345;
		when(parkingDao.findById(anyInt())).thenReturn(entry);

		// when
		ParkingEntryDto feeObject = parkingService.getFee(id);

		// then
		assertEquals(feeObject.getFee(), new BigDecimal(10));
		assertEquals(feeObject.getPlateNumber(), plate);
		assertEquals(feeObject.getStatus(), SessionStatus.FINISHED);

	}

	@Test
	public void return_fee_when_checked_by_parking_plate_number_when_parking_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		entry.setFinish(LocalDateTime.now());
		entry.setFee(new BigDecimal(10));
		List<ParkingEntry> entries = Arrays.asList(entry);
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);

		// when
		ParkingEntryDto feeObject = parkingService.getFee(plate);

		// then
		assertEquals(feeObject.getFee(), new BigDecimal(10));
		assertEquals(feeObject.getPlateNumber(), plate);
		assertEquals(feeObject.getStatus(), SessionStatus.FINISHED);

	}

	@Test
	public void return_fee_when_checked_by_parking_entry_id_when_parking_not_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		int id = 12345;
		when(parkingDao.findById(anyInt())).thenReturn(entry);
		when(feeCalculator.calculateFee(any())).thenReturn(new BigDecimal(10));

		// when
		ParkingEntryDto feeObject = parkingService.getFee(id);

		// then
		assertEquals(feeObject.getFee(), new BigDecimal(10));
		assertEquals(feeObject.getPlateNumber(), plate);
		assertEquals(feeObject.getStatus(), SessionStatus.IN_PROGRESS);

	}

	@Test
	public void return_fee_when_checked_by_parking_plate_number_when_parking_not_finished() {

		// given
		String plate = "WF12345";
		ParkingEntry entry = new ParkingEntry(plate);
		List<ParkingEntry> entries = Arrays.asList(entry);
		when(parkingDao.findByPlateNumber(any())).thenReturn(entries);
		when(feeCalculator.calculateFee(any())).thenReturn(new BigDecimal(10));

		// when
		ParkingEntryDto feeObject = parkingService.getFee(plate);

		// then
		assertEquals(feeObject.getFee(), new BigDecimal(10));
		assertEquals(feeObject.getPlateNumber(), plate);
		assertEquals(feeObject.getStatus(), SessionStatus.IN_PROGRESS);

	}

	@Test(expected = IllegalStateException.class)
	public void throw_exception_when_there_is_no_parking_entry_with_the_specified_id() {

		// given
		int id = 12345;

		// when
		parkingService.getFee(id);

	}

	@Test(expected = IllegalStateException.class)
	public void throw_exception_when_there_is_no_parking_entry_with_the_specified_plate_number() {

		// given
		String plate = "WF12345";

		// when
		parkingService.getFee(plate);
	}

	@Test
	public void return_daily_summary_object_with_default_todays_date() {

		// given
		when(feeCalculator.calculateDailyRegularTurnover(any())).thenReturn(new BigDecimal(10));
		when(feeCalculator.calculateDailyVipTurnover(any())).thenReturn(new BigDecimal(15));

		// when
		DailySummaryDto summary = parkingService.getDailySummary(LocalDate.now());

		// then
		assertEquals(summary.getSumRegular(), new BigDecimal(10));
		assertEquals(summary.getSumVip(), new BigDecimal(15));
		assertEquals(summary.getSum(), new BigDecimal(25));

	}

	////////////////////////////DO POPRAWIENIA/////////////////////////////////
	@Test
	public void return_daily_summary_object_with_a_given_date() {
		
		// given
		LocalDate date = LocalDate.now().minusWeeks(3);
		when(feeCalculator.calculateDailyRegularTurnover(any())).thenReturn(new BigDecimal(10));
		when(feeCalculator.calculateDailyVipTurnover(any())).thenReturn(new BigDecimal(15));

		// when
		DailySummaryDto summary = parkingService.getDailySummary(date);

		// then
		assertEquals(summary.getSumRegular(), new BigDecimal(10));
		assertEquals(summary.getSumVip(), new BigDecimal(15));
		assertEquals(summary.getSum(), new BigDecimal(25));

	}

	@Test(expected = IllegalArgumentException.class)
	public void throw_exception_when_the_data_entered_is_in_the_future() {

		// given
		LocalDate date = LocalDate.now().plusDays(1);

		// when
		parkingService.getDailySummary(date);

	}

}
