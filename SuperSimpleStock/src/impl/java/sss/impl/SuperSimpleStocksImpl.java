package sss.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import sss.SuperSimpleStocks;
import sss.bean.Stock;
import sss.bean.TradeRecord;

public class SuperSimpleStocksImpl implements SuperSimpleStocks {
	private final static Logger logger = Logger.getLogger(SuperSimpleStocks.class);

	/**
	 * Calculates dividend yield for a given stock. Formula differs based on stock type.
	 * COMMON stock type: LastDividend / TickerPrice
	 * PREFERRED stock type: FixedDividend x ParValue / TickerPrice
	 * @throws IllegalArgumentException when the given stock has invalid field, or when price is less than zero.
	 */
	@Override
	public double calculateDividendYield (Stock stock, double price) throws IllegalArgumentException {
		if (stock == null
				|| stock.getLastDividend() < 0
				|| stock.getFixedDividend() < 0
				|| stock.getParValue() < 0) {
			throw new IllegalArgumentException("Invalid stock was given.");
		}
		if (price <= 0) {
			throw new IllegalArgumentException("Invalid price was given.");
		}
		if (Stock.Type.COMMON == stock.getType()) {
			return stock.getLastDividend() / price;
		}
		if (Stock.Type.PREFERRED == stock.getType()) {
			return stock.getFixedDividend()*stock.getParValue() / price;
		}
		return -1;
	}

	/**
	 * Calculates P/E ratio for a given stock.
	 * Formula: TickerPrice / DividendYield
	 * @throws IllegalArgumentException when dividend yield is zero, or when price is less than zero.
	 */
	@Override
	public double calculatePERatio(Stock stock, double price) throws IllegalArgumentException {
		double dividend = this.calculateDividendYield(stock, price);
		if (dividend <= 0) {
			throw new IllegalArgumentException("Dividend can not be 0.");
		}
		if (price < 0) {
			throw new IllegalArgumentException("Price is invalid.");
		}
		return price / dividend;
	}

	/**
	 * Calculates the price of a stock, based on it's recorded trades.
	 * Formula for a given stock: sum(TradePrice x Quantity) / sum(Quantity)
	 * @throws IllegalArgumentException when the list of recorded trades is invalid or empty, or when the list has an invalid element.
	 */
	@Override
	public double calculateStockPrice(Stock.Symbol stockSymbol, List<TradeRecord> tradeRecords, LocalDateTime currentTime)
			throws IllegalArgumentException {
		if (tradeRecords == null || tradeRecords.isEmpty()) {
			throw new IllegalArgumentException("Invalid trade record list.");
		}
		if (!validTradeRecordListElements(tradeRecords)) {
			throw new IllegalArgumentException("Invalid trade record in the list.");
		}

		List<TradeRecord> filteredList = tradeRecords.stream()
				.filter(e -> (Math.abs(ChronoUnit.MINUTES.between(e.getTimeStamp(), currentTime)) < 15
						&& stockSymbol.equals(e.getStockSymbol())))
				.collect(Collectors.toList());

		if (filteredList.isEmpty()) {
			logger.warn("There is no trade record for stock " + stockSymbol + " yet.");
			return -1;
		}

		double numerator = filteredList.stream().mapToDouble(e -> e.getPrice()*e.getQuantityOfShares()).sum();
		double denominator = filteredList.stream().mapToDouble(e -> e.getQuantityOfShares()).sum();
		return numerator / denominator;
	}

	/**
	 * Calculates the GBCE All Share Index.
	 * Formula: NthRoot(StockPrice1, StockPrice2, ... , StockPriceN)
	 * @throws IllegalArgumentException when list of stocks is empty.
	 */
	@Override
	public double calculateGBCEAllShareIndex(List<Stock> stocks, List<TradeRecord> tradeRecords) throws IllegalArgumentException {
		if (stocks.isEmpty()) {
			throw new IllegalArgumentException("Stock list is epmty.");
		}
		double tradedStocks = 0;
		double result = 1;
		for (Stock stock : stocks) {
			double stockPrice = this.calculateStockPrice(stock.getSymbol(), tradeRecords, LocalDateTime.now());
			if (stockPrice != -1) {
				result *= stockPrice;
				tradedStocks++;
			}
		}
		double root = 1.0/tradedStocks;
		return Math.pow(result, root);
	}

	/**
	 * Validates the given TradeRecordList's elements.
	 * @param tradeRecords The TradeRecordList we want to validate.
	 * @return true if the list elements are valid, otherwise false.
	 */
	private boolean validTradeRecordListElements(List<TradeRecord> tradeRecords) {
		List<TradeRecord> list = tradeRecords.stream()
				.filter(e -> (e.getTimeStamp() == null
					|| e.getStockSymbol() == null
					|| e.getPrice() == 0
					|| e.getQuantityOfShares() == 0))
				.collect(Collectors.toList());
		return list.isEmpty();
	}
}
