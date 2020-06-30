package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private QuoteDao quoteDao;
    private Quote quote;

    @Before
    public void setup() {
        quoteDao.deleteAll();
        quote = new Quote();

        quote.setAskPrice(100d);
        quote.setAskSize(100);
        quote.setBidPrice(100.1d);
        quote.setBidSize(100);
        quote.setTicker("AMZN");
        quote.setLastPrice(100.1d);
        quoteDao.save(quote);
    }

    @Test
    public void findIexQuoteByTicker() {
    }

    @Test
    public void updateMarketData() {
    }

    @Test
    public void buildQuoteFromIexQuote() {
    }

    @Test
    public void saveQuote() {
    }

    @Test
    public void saveQuotes() {
    }

    @Test
    public void findAllQuotes() {
    }
}