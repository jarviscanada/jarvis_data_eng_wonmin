package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + "is invalid"));
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from the db
     * - for each ticker get iexQuote
     * - convert each iexQuote to a quote entity
     * - persist quote entities to db
     *
     * @return saved quotes
     */
    public List<Quote> updateMarketData() {
        List<String> tickers = new ArrayList<>();

        quoteDao.findAll().forEach(quote -> {
            tickers.add(quote.getTicker());
        });

        return saveQuotes(tickers);
    }

    /**
     * Helper method. Map an IexQuote object to a Quote object.
     *
     * @param iexQuote
     * @return quote object from IexQuote
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());

        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setAskPrice((double) iexQuote.getIexAskPrice());
        quote.setAskSize((int) iexQuote.getIexAskSize());
        quote.setBidPrice((double) iexQuote.getIexBidPrice());
        quote.setBidSize((int) iexQuote.getIexBidSize());

        return quote;
    }

    /**
     * Validate (against IEX) and save given tickers to quote table
     *
     * - Get IexQuotes
     * - Convert each iexQuote to Quote entity
     * - Persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @throws IllegalArgumentException if ticker is not found from IEX
     */
    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quotes = new ArrayList<>();
        List<IexQuote> iexQuotes = marketDataDao.findAllById(tickers);

        iexQuotes.forEach(iexInd -> {
            quotes.add(buildQuoteFromIexQuote(iexInd));
        });

        return quoteDao.saveAll(quotes);
    }

    /**
     * Helper Method to update a given quote with specified ID
     *
     * @param ticker quote ID
     */
    public Quote saveQuote(String ticker) {
        Optional<IexQuote> iexQuoteOptional = marketDataDao.findById(ticker);
        Quote quoteFromIex = buildQuoteFromIexQuote(iexQuoteOptional.get());

        return quoteDao.save(quoteFromIex);

    }

    /**
     * Update a given quote to quote table without validation
     *
     * @param quote entity
     */
    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    /**
     * Find all quotes from the quote table
     * @return a list of quotes
     */
    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }

}
