package carparks.parking;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FeeCalculatorShould {

	private ParkingEntry testedEntry;
	private BigDecimal expectedResult;
	private FeeCalculator feeCalculator;

	@Before
	public void initialize() {
		feeCalculator = new FeeCalculator();
	}

	public FeeCalculatorShould(ParkingEntry testedEntry, BigDecimal expectedResult) {
		this.testedEntry = testedEntry;
		this.expectedResult = expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection testParameters() {
		
		String plate = "WF12345";
		
		// Regular - more than 1 hour, but less than 2 hours
		ParkingEntry entry1 = new ParkingEntry(plate, FeeType.REGULAR);
		entry1.setFinish(entry1.getStart().plusMinutes(61));
		
		// Regular - exactly 0 hours
		ParkingEntry entry2 = new ParkingEntry(plate, FeeType.REGULAR);
		entry2.setFinish(entry2.getStart());
		
		// Regular - 0 hours and 20 minutes
		ParkingEntry entry3 = new ParkingEntry(plate, FeeType.REGULAR);
		entry3.setFinish(entry3.getStart().plusMinutes(20));
		
		// Regular - exactly 3 hours and not a second longer
		ParkingEntry entry4 = new ParkingEntry(plate, FeeType.REGULAR);
		entry4.setFinish(entry4.getStart().plusHours(3));
		
		// Regular - 5 hours and 1 second
		ParkingEntry entry5 = new ParkingEntry(plate, FeeType.REGULAR);
		entry5.setFinish(entry5.getStart().plusHours(5).plusSeconds(1));
		
		// VIP - more than 1 hour, but less than 2 hours
		ParkingEntry entry6 = new ParkingEntry(plate, FeeType.VIP);
		entry6.setFinish(entry6.getStart().plusMinutes(61));
		
		// VIP - 0 hours
		ParkingEntry entry7 = new ParkingEntry(plate, FeeType.VIP);
		entry7.setFinish(entry7.getStart());
		
		// VIP - exactly 1 hour
		ParkingEntry entry8 = new ParkingEntry(plate, FeeType.VIP);
		entry8.setFinish(entry8.getStart().plusHours(1));
		
		// VIP - exactly 2 hours
		ParkingEntry entry9 = new ParkingEntry(plate, FeeType.VIP);
		entry9.setFinish(entry9.getStart().plusHours(2));
		
		// VIP - 5 hours and 1 second (also checks rounding)
		ParkingEntry entry10 = new ParkingEntry(plate, FeeType.VIP);
		entry10.setFinish(entry10.getStart().plusHours(5).plusSeconds(1));
		
		
		return Arrays.asList(new Object[][] {
	         {entry1, new BigDecimal(3).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry2, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry3, new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry4, new BigDecimal(7.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry5, new BigDecimal(63.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry6, new BigDecimal(2.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry7, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry8, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry9, new BigDecimal(2.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {entry10, new BigDecimal(26.38).setScale(2, BigDecimal.ROUND_HALF_UP)},
	      });
	   }
	  
	 @Test
	 public void return_the_expected_fee() {
		 assertEquals(expectedResult, feeCalculator.calculateFee(testedEntry));
	 }

}
