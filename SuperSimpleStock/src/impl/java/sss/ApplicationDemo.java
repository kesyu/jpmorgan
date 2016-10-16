package sss;

import sss.bean.Stock;
import sss.impl.SuperSimpleStocksImpl;
import sss.service.StockService;
import sss.service.TradeService;
import sss.service.TradeService.TradeType;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;

public class ApplicationDemo {
	private final static Logger logger = Logger.getLogger(ApplicationDemo.class);

	public static void main(String[] args) {
		try {
			logger.info("Welcome to Super Simple Stock!");
			logger.info("Starting Application demo...");
			StockService stockService = new StockService();
			TradeService tradeService = new TradeService();
			SuperSimpleStocks superSimpleStocks = new SuperSimpleStocksImpl();
			
			logger.info("##### Trading Demo: #####");
			tradeService.tradeStock(TradeType.BUY, Stock.Symbol.ALE, 10, 80);
			tradeService.tradeStock(TradeType.BUY, Stock.Symbol.ALE, 3, 65);
			tradeService.tradeStock(TradeType.SELL, Stock.Symbol.GIN, 5, 150);
			
			logger.info("##### SuperSimpleStock Demo: #####");
			logger.info("Dividend Yield for Common stock ALE:");
			logger.info(">>> Result: "
					+ superSimpleStocks.calculateDividendYield(stockService.getStockBySymbol(Stock.Symbol.ALE), 10));
			logger.info("Dividend Yield for Preferred stock GIN:");
			logger.info(">>> Result: "
					+ superSimpleStocks.calculateDividendYield(stockService.getStockBySymbol(Stock.Symbol.GIN), 10));
			
			logger.info("Calculate P/E ratio for stock ALE:");
			logger.info(">>> Result: "
					+ superSimpleStocks.calculatePERatio(stockService.getStockBySymbol(Stock.Symbol.ALE), 10));
			
			logger.info("Calculate stock price for stock ALE:");
			logger.info(">>> Result: "
					+ superSimpleStocks.calculateStockPrice(Stock.Symbol.ALE, tradeService.getTradeRecords(), LocalDateTime.now()));
			
			logger.info("Calculate GBCE all share index:");
			logger.info(">>> Result: "
					+ superSimpleStocks.calculateGBCEAllShareIndex(stockService.getStocks(), tradeService.getTradeRecords()));
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
		}
	}

}
