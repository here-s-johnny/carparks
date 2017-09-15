package carparks.parking;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mock;

import carparks.parking.ParkingDao;
import carparks.parking.ParkingService;

public class ParkingServiceTest {

	@Mock
	private ParkingDao parkingDao;

	private ParkingService parkingService = new ParkingService(parkingDao);

	@Test
	public void parking_entry_added_successfully() {

		// rozpocznij parkowanie dla samochodu
//		parkingService.createEntry("WF12345");

		
		// assert ze dodane

	}

	@Test
	public void parking_entry_unsuccessfull__parking_already_started() {

		// rozpocznij parkowanie dla samochodu

		// sprobuj rozpoczac parkowanie dla tego samego samochodu

		// assert fail

	}

	@Test
	public void parking_finished_successfully() {

		// rozpocznij parkowanie dla samochodu

		// zakoncz parkowanie dla samochodu

		// rozpocznij parkowanie dla tego samego samochodu

	}

	@Test
	public void parking_finished_unsuccessfull__finishing_without_starting() {

		// zakoncz parkowanie dla samochodu

		// assert fail

	}

	@Test
	public void check_if_parking_started_for_a_vehicle__started() {

		// rozpocznij parkowanie dla samochodu

		// sprawdz czy rozpoczete - osobna metoda potrzebna dla parking ownera
	}

	@Test
	public void check_if_parking_started_for_a_vehicle__not_started() {

		// sprawdz czy rozpoczete - fail

	}

	@Test
	public void check_if_parking_started_for_a_vehicle__started_and_finished() {

		// rozpocznij parkowanie dla samochodu

		// zakoncz parkowanie

		// sprawdz czy rozpoczete - fail
	}

	@Test
	public void check_fee_by_parking_entry_id__parking_finished() {

		// rozpocznij parkowanie dla samochodu

		// zakoncz parkowanie

		// sprawdz fee

	}

	@Test
	public void check_fee_by_plate_number__parking_finished() {

		// rozpocznij parkowanie dla samochodu

		// zakoncz parkowanie

		// sprawdz fee (inna metoda)

	}

	@Test
	public void check_fee_by_parking_entry_id__parking_not_finished() {

		// rozpocznij parkowanie dla samochodu

		// sprawdz fee

	}

	@Test
	public void check_fee_by_plate_number__parking_not_finished() {

		// rozpocznij parkowanie dla samochodu

		// sprawdz fee (inna metoda)

	}
	
	@Test
	public void check_fee_by_parking_entry_id__no_such_id() {

		// sprawdz fee
		
		// assert ze nie ma

	}

	@Test
	public void check_fee_by_plate_number__no_such_plate_number() {

		// sprawdz fee (inna metoda)
		
		// assert ze nie ma

	}
	
	@Test
	public void view_summary() {
		
		// parametryzowany test dla róznych wartosci VIP i regular
		
	}
	

	

}
