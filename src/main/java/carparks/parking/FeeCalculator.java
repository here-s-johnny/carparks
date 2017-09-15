package carparks.parking;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
class FeeCalculator {

	public BigDecimal calculateFee(ParkingEntry entry) {
		
		return new BigDecimal(10);
	}
	
	public BigDecimal calculateDailyVipTurnover(LocalDate date) {
		
		return new BigDecimal(10);
	}
	
	public BigDecimal calculateDailyRegularTurnover(LocalDate date) {
		
		return new BigDecimal(10);
	}
	
}
