package carparks.parking;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
interface CurrencyConverter {

	boolean supports(String currency);
	BigDecimal convert(BigDecimal amountInPln);
	
}
