package carparks.parking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("Parking repository")
interface ParkingDao extends CrudRepository<ParkingEntry, Integer> {
	
	public ParkingEntry findById(int id);
	
	public List<ParkingEntry> findByPlateNumber(String plateNumber);
	
}
