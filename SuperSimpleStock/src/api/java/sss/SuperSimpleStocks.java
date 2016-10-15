package sss;

import sss.bean.Stock;
import sss.bean.TradeRecord;

public interface SuperSimpleStocks {
	double calculateDividendYield(Stock stock, double price);
	double calculatePERatio(Stock stock, double price);
	void tradeStock(TradeRecord trade, double quantity);
	double calculateStockPriceFromRecordedTrades();
	double calculateGBCEAllShareIndex();
}
