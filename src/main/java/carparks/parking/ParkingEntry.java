package carparks.parking;

import java.math.BigDecimal;
import java.time.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking_entry")
class ParkingEntry {
	
	@Column(name = "id", unique = true)
	@NotNull
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "plate_number")
	private String plateNumber;
	
	@Column(name = "start")
	private LocalDate start;
	
	@Column(name = "finish")
	private LocalDate finish;
	
	@Column(name = "fee")
	private BigDecimal fee;

	public ParkingEntry(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	// no-argument package-scope constructor for Hibernate
	ParkingEntry() {}
	
	
	
	

}
