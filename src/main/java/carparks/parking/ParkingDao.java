package carparks.parking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository("Parking repository")
@RepositoryRestResource(collectionResourceRel = "ParkingEntries", path = "entries")
interface ParkingDao extends CrudRepository<ParkingEntry, Integer> {
	
	public ParkingEntry findById(int id);
	
	public List<ParkingEntry> findByPlateNumber(String plateNumber);
	
	public List<ParkingEntry> findByFeeTypeAndFinishBetween(FeeType feeType, LocalDateTime start, LocalDateTime finish);
	
}
