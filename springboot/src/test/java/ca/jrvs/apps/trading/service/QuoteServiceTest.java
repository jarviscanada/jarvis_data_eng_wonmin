package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteDao quoteDao;

    @Before
    public void setQuotes() {
        quoteDao.deleteAll();
    }

    @Test
    public void findIexQuoteByTicker() {
        Quote savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setID("AAPL");
        savedQuote.setLastPrice(10.1d);

        IexQuote returnQuote = quoteService.findIexQuoteByTicker("AAPL");

        assertNotNull(returnQuote);
        assertEquals(savedQuote.getTicker(), returnQuote.getSymbol());
    }

    @Test
    public void updateMarketData() {
        Quote savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setID("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        List<Quote> quotes = quoteService.updateMarketData();

        assertNotNull(quotes);
        assertEquals(savedQuote.getTicker(), quotes.get(0).getTicker());
    }

    @Test
    public void buildQuoteFromIexQuote() {
        IexQuote returnQuote = quoteService.findIexQuoteByTicker("AAPL");
        Quote builtQuote = quoteService.buildQuoteFromIexQuote(returnQuote);
        Double askPrice = (double) returnQuote.getIexAskPrice();

        assertEquals(returnQuote.getSymbol(), builtQuote.getTicker());
        assertEquals(askPrice, builtQuote.getAskPrice());
    }

    @Test
    public void saveQuote() {
        Quote newQuote = new Quote();
        newQuote.setAskPrice(10d);
        newQuote.setAskSize(10);
        newQuote.setBidPrice(10.2d);
        newQuote.setBidSize(10);
        newQuote.setID("AAPL");
        newQuote.setLastPrice(10.1d);

        Quote savedQuote = quoteService.saveQuote(newQuote);

        assertEquals(newQuote.getTicker(), savedQuote.getTicker());
        assertEquals(newQuote.getAskPrice(), savedQuote.getAskPrice());
    }

    @Test
    public void saveQuotes() {
        List<String> tickers = new ArrayList<>();
        tickers.add("AAPL"); tickers.add("FB");

        List<Quote> quotes = quoteService.saveQuotes(tickers);

        assertNotNull(quotes);
        assertEquals("AAPL", quotes.get(0).getTicker());
        assertEquals("FB", quotes.get(1).getTicker());
    }

    @Test
    public void findAllQuotes() {
        Quote savedQuote = new Quote();
        Quote savedQuote2 = new Quote();

        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setID("AAPL");
        savedQuote.setLastPrice(10.1d);

        savedQuote2.setAskPrice(20d);
        savedQuote2.setAskSize(13);
        savedQuote2.setBidPrice(12.2d);
        savedQuote2.setBidSize(30);
        savedQuote2.setID("FB");
        savedQuote2.setLastPrice(12.5d);

        quoteDao.save(savedQuote);
        quoteDao.save(savedQuote2);

        List<Quote> quotes = quoteService.findAllQuotes();

        assertNotNull(quotes);
        assertEquals(savedQuote.getTicker(), quotes.get(0).getTicker());
        assertEquals(savedQuote2.getTicker(), quotes.get(1).getTicker());
    }

}