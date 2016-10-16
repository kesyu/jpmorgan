package sss.impl;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sss.SuperSimpleStocks;
import sss.bean.Stock;

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
	    SuperSimpleStocks spySuperSimpleStocks= Mockito.spy(superSimpleStocks);

	    Mockito.doReturn(dividend).when(spySuperSimpleStocks).calculateDividendYield(Mockito.any(Stock.class), Mockito.anyDouble()); 

		spySuperSimpleStocks.calculatePERatio(stock, tickerPrice);
	}

	@Test
	public void testCalculatePERatio() {
		double dividend = 5.0;
		double tickerPrice = 2.0;
		double expected = 0.4;
	    SuperSimpleStocks spySuperSimpleStocks= Mockito.spy(superSimpleStocks);

	    Mockito.doReturn(dividend).when(spySuperSimpleStocks).calculateDividendYield(Mockito.any(Stock.class), Mockito.anyDouble()); 

	    Assert.assertTrue(spySuperSimpleStocks.calculatePERatio(stock, tickerPrice) == expected);
	}

	@Test
	public void testCalculateStockPriceFromRecordedTrades() {
		superSimpleStocks.calculateStockPriceFromRecordedTrades();
	}

	@Test
	public void testCalculateGBCEAllShareIndex() {
		superSimpleStocks.calculateGBCEAllShareIndex();
	}
}
