package carparks.parking;

import java.math.BigDecimal;

public class DailySummaryDto {
	
	private BigDecimal sumRegular;
	private BigDecimal sumVip;
	private BigDecimal sum;
	
	
	public DailySummaryDto(BigDecimal sumRegular, BigDecimal sumVip) {
		this.sumRegular = sumRegular;
		this.sumVip = sumVip;
		this.sum = sumRegular.add(sumVip);
	}


	public BigDecimal getSumRegular() {
		return sumRegular;
	}


	public BigDecimal getSumVip() {
		return sumVip;
	}
	
	public BigDecimal getSum() {
		return sum;
	}
	
	

}
