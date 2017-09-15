package carparks.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {

	private ParkingDao parkingDao;
	
	@Autowired
	public ParkingService(ParkingDao parkingDao) {
		this.parkingDao = parkingDao;
	}
	
}
