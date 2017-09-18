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
	
	@Column(name = "type")
	private FeeType feeType;
	
	@Column(name = "start")
	private LocalDateTime start;
	
	@Column(name = "finish")
	private LocalDateTime finish;
	
	@Column(name = "fee")
	private BigDecimal fee;

	public ParkingEntry(String plateNumber, FeeType feeType) {
		this.plateNumber = plateNumber;
		this.start = LocalDateTime.now();
		this.finish = null;
		this.feeType = feeType;
	}
	
	// no-argument package-scope constructor for Hibernate
	ParkingEntry() {}

	public LocalDateTime getFinish() {
		return finish;
	}

	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public String getPlateNumber() {
		return plateNumber;
	}
	
	public FeeType getFeeType() {
		return feeType;
	}
	
	
	

}
