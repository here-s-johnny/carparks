package carparks.parking;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
class ParkingEntryDto {

	private String plateNumber;
	private BigDecimal fee;
	private SessionStatus status;
	
	public ParkingEntryDto(String plateNumber, BigDecimal fee, SessionStatus status) {
		this.plateNumber = plateNumber;
		this.fee = fee;
		this.status = status;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public BigDecimal getFee() {
		return fee;
	}
	
	public SessionStatus getStatus() {
		return status;
	}
	
}
