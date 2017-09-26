package carparks.parking;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
class FeeCalculator {

	public BigDecimal calculateFee(ParkingEntry entry) {

		LocalDateTime start = entry.getStart();
		LocalDateTime end = (entry.getFinish() == null) ? LocalDateTime.now() : entry.getFinish();
		Duration duration = Duration.between(start, end);
		
		long durationInHours = duration.getSeconds() / 3600;
		
		// if the duration is even one second longer than the full hour
		// we want to add fee for the next started hour
		long hours = ((duration.getSeconds() % 3600) == 0) ? durationInHours
				: durationInHours + 1;
		
		BigDecimal sum = new BigDecimal(0);
		FeeSettings settings;

		if (entry.getFeeType() == FeeType.REGULAR) {
			settings = new FeeSettings(0, new BigDecimal(2), new BigDecimal(1));
		} else {
			settings = new FeeSettings(1, new BigDecimal(1.5), new BigDecimal(2));
		}
		
		BigDecimal hourFee = settings.getHourFee();
		BigDecimal multiplier = settings.getMultiplier();
		int hour = settings.getFirstChargedHour();
		
		for (int i = hour; i < hours; ++i) {
			sum = sum.add(hourFee);
			hourFee = hourFee.multiply(multiplier);
		}

		return sum.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public BigDecimal calculateDailyTurnover(List<ParkingEntry> entries) {

		BigDecimal sum = entries.stream().map(entry -> entry.getFee()).reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return sum.setScale(2, BigDecimal.ROUND_HALF_UP);
	}


}
