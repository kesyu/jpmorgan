package sss;

import sss.bean.Stock;

public interface SuperSimpleStocks {
	double calculateDividendYield(Stock stock, double price);
	double calculatePERatio(Stock stock, double price);
	double calculateStockPriceFromRecordedTrades();
	double calculateGBCEAllShareIndex();
}
