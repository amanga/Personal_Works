package com.bunge.trading.pojo;

public class StockTicker {

	private String ticker;
	private Double currentValue;

	public StockTicker() {

	}

	public StockTicker(String ticker, Double currentValue) {
		this.ticker = ticker;
		this.currentValue = currentValue;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Double currentValue) {
		this.currentValue = currentValue;
	}

}
