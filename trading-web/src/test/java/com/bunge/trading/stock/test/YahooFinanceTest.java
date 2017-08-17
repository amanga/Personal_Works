package com.bunge.trading.stock.test;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class YahooFinanceTest {
	
	@Before
	public void init(){
		System.out.println("init");
	}

	@Test
	public void test1(){
		System.out.println("Test 1");
		Stock stock;
		try {
			for(int i =0 ; i< 1000; i++){
				stock = YahooFinance.get("nvda");
				
				BigDecimal price = stock.getQuote().getPrice();
				BigDecimal change = stock.getQuote().getChangeInPercent();
				System.out.println("Change: "+change);
				stock.print();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	@After
	public void destroy(){
		System.out.println("destroy");
	}
}
