package carparks.parking;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class RegularFeeCalculatorShould {

	private ParkingEntry testedEntry;
	private BigDecimal expectedResult;
	private FeeCalculator feeCalculator;

	@Before
	public void initialize() {
		feeCalculator = new FeeCalculator();
	}

	public RegularFeeCalculatorShould(ParkingEntry testedEntry, BigDecimal expectedResult) {
		this.testedEntry = testedEntry;
		this.expectedResult = expectedResult;
	}
	
	@Parameterized.Parameters
	public static Collection testParameters() {
		
		String plate = "WF12345";
		
		ParkingEntry more_than_one_hour_less_than_two = new ParkingEntry(plate, FeeType.REGULAR);
		more_than_one_hour_less_than_two.setFinish(more_than_one_hour_less_than_two.getStart().plusMinutes(61));
		
		ParkingEntry exatcly_zero_hours = new ParkingEntry(plate, FeeType.REGULAR);
		exatcly_zero_hours.setFinish(exatcly_zero_hours.getStart());
		
		ParkingEntry twenty_minutes = new ParkingEntry(plate, FeeType.REGULAR);
		twenty_minutes.setFinish(twenty_minutes.getStart().plusMinutes(20));
		
		ParkingEntry exatcly_3_hours = new ParkingEntry(plate, FeeType.REGULAR);
		exatcly_3_hours.setFinish(exatcly_3_hours.getStart().plusHours(3));
		
		ParkingEntry five_hours_and_one_second = new ParkingEntry(plate, FeeType.REGULAR);
		five_hours_and_one_second.setFinish(five_hours_and_one_second.getStart().plusHours(5).plusSeconds(1));
		
		ParkingEntry more_than_one_hour_less_than_two_vip = new ParkingEntry(plate, FeeType.VIP);
		more_than_one_hour_less_than_two_vip.setFinish(more_than_one_hour_less_than_two_vip.getStart().plusMinutes(61));
		
		ParkingEntry zero_hours_vip = new ParkingEntry(plate, FeeType.VIP);
		zero_hours_vip.setFinish(zero_hours_vip.getStart());
		
		ParkingEntry exatcly_one_hour_vip = new ParkingEntry(plate, FeeType.VIP);
		exatcly_one_hour_vip.setFinish(exatcly_one_hour_vip.getStart().plusHours(1));
		
		ParkingEntry exactly_two_hours = new ParkingEntry(plate, FeeType.VIP);
		exactly_two_hours.setFinish(exactly_two_hours.getStart().plusHours(2));
		
		ParkingEntry five_hours_and_one_second_vip = new ParkingEntry(plate, FeeType.VIP);
		five_hours_and_one_second_vip.setFinish(five_hours_and_one_second_vip.getStart().plusHours(5).plusSeconds(1));
		
		
		return Arrays.asList(new Object[][] {
	         {more_than_one_hour_less_than_two, new BigDecimal(3).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {exatcly_zero_hours, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {twenty_minutes, new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {exatcly_3_hours, new BigDecimal(7.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {five_hours_and_one_second, new BigDecimal(63.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {more_than_one_hour_less_than_two_vip, new BigDecimal(2.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {zero_hours_vip, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {exatcly_one_hour_vip, new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {exactly_two_hours, new BigDecimal(2.00).setScale(2, BigDecimal.ROUND_HALF_UP)},
	         {five_hours_and_one_second_vip, new BigDecimal(26.38).setScale(2, BigDecimal.ROUND_HALF_UP)},
	      });
	   }
	  
	 @Test
	 public void return_the_expected_fee() {
		 assertEquals(expectedResult, feeCalculator.calculateFee(testedEntry));
	 }

}
