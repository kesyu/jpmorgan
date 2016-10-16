package sss.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import sss.bean.Stock;
import sss.bean.TradeRecord;
import org.apache.log4j.Logger;

public class TradeService {
	private final static Logger logger = Logger.getLogger(TradeService.class);
	List<TradeRecord> tradeRecords;
	
	public enum TradeType {
		BUY, SELL
	}
	
	public TradeService() {
		tradeRecords = new LinkedList<>();
	}
	
	public List<TradeRecord> getTradeRecords() {
		return tradeRecords;
	}
	
	public void tradeStock(TradeType tradeType, Stock.Symbol stockSymbol, int quantityOfShares, int price) {
		logger.info("Trading Stock: " + tradeType + "ing " + quantityOfShares + " " + stockSymbol + " stock for " + price + ".");
		TradeRecord tradeRecord = new TradeRecord(tradeType, stockSymbol, quantityOfShares, price, LocalDateTime.now());
		tradeRecords.add(tradeRecord);
		logger.info("Trade complete.");
	}
}
