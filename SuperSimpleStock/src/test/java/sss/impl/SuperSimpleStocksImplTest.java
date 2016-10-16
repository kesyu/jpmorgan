package sss.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sss.SuperSimpleStocks;
import sss.bean.Stock;
import sss.bean.TradeRecord;

public class SuperSimpleStocksImplTest {
	private SuperSimpleStocks superSimpleStocks;
	private Stock stock;

	@BeforeTest
	public void setup() {
		superSimpleStocks = new SuperSimpleStocksImpl();
		stock = new Stock();
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCalculateDividendYieldWithError() {
		Stock nullStock = null;
		superSimpleStocks.calculateDividendYield(nullStock, 2);
	}

	@DataProvider(name="dividendYieldErrorData")
	private Object[][] dividendYieldErrorData() {
		return new Object[][] {
			{Stock.Type.COMMON, 8, 0.02f, 100, 0.0},
			{Stock.Type.COMMON, 8, 0.02f, 100, -2.0},
			{Stock.Type.COMMON, -8, 0.02f, 100, 2.0},
			{Stock.Type.PREFERRED, 8, 0.02f, 100, 0.0},
			{Stock.Type.PREFERRED, 8, 0.02f, 100, -2.0},
			{Stock.Type.PREFERRED, 8, 0.02f, -100, 2.0},
			{Stock.Type.PREFERRED, 8, -0.02f, 100, 2.0}
		};
	}

	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider="dividendYieldErrorData")
	public void testCalculateDividendYieldWithError(Stock.Type stockType, int lastDivinded, float fixedDividend, int parValue, double tickerPrice) {
		stock.setType(stockType);
		stock.setLastDividend(lastDivinded);
		stock.setFixedDividend(fixedDividend);
		stock.setParValue(parValue);
		superSimpleStocks.calculateDividendYield(stock, tickerPrice);
	}

	@DataProvider(name="dividendYieldDataCommonStock")
	private Object[][] dividendYieldDataCommonStock() {
		return new Object[][] {
			{Stock.Type.COMMON, 8, 2.0, 4.0},
			{Stock.Type.COMMON, 8, 5.0, 1.6},
			{Stock.Type.COMMON, 0, 2.0, 0.0},
		};
	}

	@Test(dataProvider="dividendYieldDataCommonStock")
	public void testCalculateDividendYieldCommonStock(Stock.Type stockType, int lastDivinded, double tickerPrice, double expected) {
		stock.setType(stockType);
		stock.setLastDividend(lastDivinded);
		Assert.assertTrue(superSimpleStocks.calculateDividendYield(stock, tickerPrice) == expected); 
	}

	@DataProvider(name="dividendYieldDataPreferredStock")
	private Object[][] dividendYieldDataPreferredStock() {
		return new Object[][] {
			{Stock.Type.PREFERRED, 100, 0.02f, 2.0, 1.0},
			{Stock.Type.PREFERRED, 0, 0.02f, 2.0, 0.0},
		};
	}

	@Test(dataProvider="dividendYieldDataPreferredStock")
	public void testCalculateDividendYieldPreferredStock(Stock.Type stockType, int parValue, float fixedDividend, double tickerPrice, double expected) {
		stock.setType(stockType);
		stock.setParValue(parValue);
		stock.setFixedDividend(fixedDividend);

		Assert.assertTrue(superSimpleStocks.calculateDividendYield(stock, tickerPrice) == expected); 
	}

	@DataProvider(name="peRatioErrorData")
	private Object[][] peRatioErrorData() {
		return new Object[][] {
			{0.0, 2.0},
			{-5.0, 2.0},
			{5.0, -2.0}
		};
	}

	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider="peRatioErrorData")
	public void testCalculatePERatioWithError(double dividend, double tickerPrice) {
	    SuperSimpleStocks spySuperSimpleStocks = Mockito.spy(superSimpleStocks);

	    Mockito.doReturn(dividend).when(spySuperSimpleStocks).calculateDividendYield(Mockito.any(Stock.class), Mockito.anyDouble()); 

		spySuperSimpleStocks.calculatePERatio(stock, tickerPrice);
	}

	@Test
	public void testCalculatePERatio() {
		double dividend = 5.0;
		double tickerPrice = 2.0;
		double expected = 0.4;
	    SuperSimpleStocks spySuperSimpleStocks = Mockito.spy(superSimpleStocks);

	    Mockito.doReturn(dividend).when(spySuperSimpleStocks).calculateDividendYield(Mockito.any(Stock.class), Mockito.anyDouble()); 

	    Assert.assertTrue(spySuperSimpleStocks.calculatePERatio(stock, tickerPrice) == expected);
	}

	@DataProvider(name="tradeRecordListErrorData")
	private Object[][] tradeRecordListErrorData() {
		TradeRecord invalidTradeRecord = new TradeRecord();
		invalidTradeRecord.setPrice(10);
		invalidTradeRecord.setQuantityOfShares(2);
		invalidTradeRecord.setStockSymbol(Stock.Symbol.ALE);
		return new Object[][] {
			{null},
			{Collections.emptyList()},
			{Arrays.asList(new TradeRecord())},
			{Arrays.asList(invalidTradeRecord)}
		};
	}

	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider="tradeRecordListErrorData")
	public void testCalculateStockPriceWithError(List<TradeRecord> tradeRecords) {
		LocalDateTime currentTime = LocalDateTime.of(2016, 10, 16, 10, 50);

		superSimpleStocks.calculateStockPrice(Stock.Symbol.ALE, tradeRecords, currentTime);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class, dataProvider="tradeRecordListErrorData")
	public void testCalculateStockPriceWithNoTradeRecord(List<TradeRecord> tradeRecords) {
		LocalDateTime currentTime = LocalDateTime.of(2016, 10, 16, 10, 50);

		Assert.assertTrue(superSimpleStocks.calculateStockPrice(Stock.Symbol.POP, tradeRecords, currentTime) == -1);
	}
	
	@Test
	public void testCalculateStockPriceFromRecordedTrades() {
		LocalDateTime currentTime = LocalDateTime.of(2016, 10, 16, 10, 50);
		List<TradeRecord> tradeRecords = setupTradeRecordList();

		Assert.assertTrue(superSimpleStocks.calculateStockPrice(Stock.Symbol.ALE, tradeRecords, currentTime) == 4.0);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCalculateGBCEAllShareIndexWithError() {
		List<TradeRecord> tradeRecords = setupTradeRecordList();
	    superSimpleStocks.calculateGBCEAllShareIndex(Collections.emptyList(), tradeRecords);
	}

	private List<TradeRecord> setupTradeRecordList() {
		List<TradeRecord> tradeRecords = new LinkedList<TradeRecord>();
		TradeRecord tradeRecord1 = new TradeRecord();
		tradeRecord1.setPrice(2);
		tradeRecord1.setStockSymbol(Stock.Symbol.ALE);
		tradeRecord1.setQuantityOfShares(2);
		tradeRecord1.setTimeStamp(LocalDateTime.of(2016, 10, 16, 10, 55));
		tradeRecords.add(tradeRecord1);

		TradeRecord tradeRecord2 = new TradeRecord();
		tradeRecord2.setPrice(4);
		tradeRecord2.setStockSymbol(Stock.Symbol.GIN);
		tradeRecord2.setQuantityOfShares(2);
		tradeRecord2.setTimeStamp(LocalDateTime.of(2016, 10, 16, 10, 55));
		tradeRecords.add(tradeRecord2);

		TradeRecord tradeRecord3 = new TradeRecord();
		tradeRecord3.setPrice(6);
		tradeRecord3.setStockSymbol(Stock.Symbol.ALE);
		tradeRecord3.setQuantityOfShares(2);
		tradeRecord3.setTimeStamp(LocalDateTime.of(2016, 10, 16, 10, 45));
		tradeRecords.add(tradeRecord3);

		TradeRecord tradeRecord4 = new TradeRecord();
		tradeRecord4.setPrice(8);
		tradeRecord4.setStockSymbol(Stock.Symbol.ALE);
		tradeRecord4.setQuantityOfShares(2);
		tradeRecord4.setTimeStamp(LocalDateTime.of(2016, 10, 16, 10, 20));
		tradeRecords.add(tradeRecord4);

		return tradeRecords;
	}
}
