package sss;

import sss.bean.Stock;
import sss.bean.TradeRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface SuperSimpleStocks {
	double calculateDividendYield(Stock stock, double price);
	double calculatePERatio(Stock stock, double price);
	double calculateStockPrice(Stock.Symbol stockSymbol, List<TradeRecord> tradeRecords, LocalDateTime currentTime);
	double calculateGBCEAllShareIndex(List<Stock> stocks, List<TradeRecord> tradeRecords);
}
