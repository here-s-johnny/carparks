package carparks.parking;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FeeCalculatorDailyTurnoverShould {

	private FeeCalculator feeCalculator = new FeeCalculator();

	@Test
	public void return_the_expected_daily_turnover() {

		// given
		String plate = "WF12345";
		ParkingEntry entry1 = new ParkingEntry(plate, FeeType.REGULAR);
		entry1.setFee(new BigDecimal(10));
		ParkingEntry entry2 = new ParkingEntry(plate, FeeType.REGULAR);
		entry2.setFee(new BigDecimal(20));
		ParkingEntry entry3 = new ParkingEntry(plate, FeeType.REGULAR);
		entry3.setFee(new BigDecimal(30));
		ParkingEntry entry4 = new ParkingEntry(plate, FeeType.REGULAR);
		entry4.setFee(new BigDecimal(40));

		List<ParkingEntry> entries = Arrays.asList(entry1, entry2, entry3, entry4);

		// when
		BigDecimal turnover = feeCalculator.calculateDailyTurnover(entries);

		// then
		assertEquals(turnover, new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP));

	}

}
