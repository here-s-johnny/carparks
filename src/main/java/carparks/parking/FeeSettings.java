package carparks.parking;

import java.math.BigDecimal;

class FeeSettings {
	
	private int firstChargedHour;
	private BigDecimal multiplier;
	private BigDecimal hourFee;
	
	
	public FeeSettings(int firstChargedHour, BigDecimal multiplier, BigDecimal hourFee) {
		this.firstChargedHour = firstChargedHour;
		this.multiplier = multiplier;
		this.hourFee = hourFee;
	}


	public int getFirstChargedHour() {
		return firstChargedHour;
	}


	public BigDecimal getMultiplier() {
		return multiplier;
	}


	public BigDecimal getHourFee() {
		return hourFee;
	}
	
	
	
	

}
