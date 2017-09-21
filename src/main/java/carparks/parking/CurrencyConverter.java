package carparks.parking;

import java.math.BigDecimal;

interface CurrencyConverter {

	boolean supports(String currency);
	BigDecimal convert(BigDecimal amountInPln);
	
}
