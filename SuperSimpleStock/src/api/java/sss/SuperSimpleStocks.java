package sss;

import sss.bean.Stock;
import sss.bean.TradeRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface SuperSimpleStocks {
	/**
	 * Calculates dividend yield for a given stock.
	 * @param stock The stock we want to calculate dividend yield for.
	 * @param price Ticker price
	 * @return Calculated dividend yield for the given stock.
	 */
	double calculateDividendYield(Stock stock, double price);

	/**
	 * Calculates P/E ratio for a given stock.
	 * @param stock The stock we want to calculate dividend yield for.
	 * @param price Ticker price
	 * @return Calculated P/E ratio for the given stock.
	 */
	double calculatePERatio(Stock stock, double price);

	/**
	 * Calculates the price of a stock, based on it's recorded trades.
	 * @param stockSymbol Given stock's symbol.
	 * @param tradeRecords List of trades made since the application started.
	 * @param currentTime The current time.
	 * @return Calculated price for the given stock.
	 */
	double calculateStockPrice(Stock.Symbol stockSymbol, List<TradeRecord> tradeRecords, LocalDateTime currentTime);

	/**
	 * Calculates the GBCE All Share Index.
	 * @param stocks List of existing stocks.
	 * @param tradeRecords List of trades made since the application started.
	 * @return Calculated GBCE All Share Index.
	 */
	double calculateGBCEAllShareIndex(List<Stock> stocks, List<TradeRecord> tradeRecords);
}
