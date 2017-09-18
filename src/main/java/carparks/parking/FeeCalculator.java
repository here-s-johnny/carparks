package carparks.parking;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
class FeeCalculator {

	public BigDecimal calculateFee(ParkingEntry entry) {

		Duration duration = Duration.between(entry.getStart(), entry.getFinish());
		long hours = ((duration.getSeconds() % 3600) == 0) ? duration.getSeconds() / 3600
				: duration.getSeconds() / 3600 + 1;
		
		BigDecimal sum = new BigDecimal(0);
		BigDecimal hourFee;
		BigDecimal multiplier;
		int i;

		if (entry.getFeeType() == FeeType.REGULAR) {
			hourFee  = new BigDecimal(1);
			multiplier = new BigDecimal(2);
			i = 0;
		} else {
			hourFee  = new BigDecimal(2);
			multiplier = new BigDecimal(1.5);
			i = 1;
		}
		
		while (i < hours) {
			sum = sum.add(hourFee);
			hourFee = hourFee.multiply(multiplier);
			++i;
		}

		return sum.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public BigDecimal calculateDailyTurnover(List<ParkingEntry> entries) {

		BigDecimal sum = new BigDecimal(0);
		
		for (ParkingEntry e : entries) {
			sum = sum.add(e.getFee());
		}
		
		return sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}


}
