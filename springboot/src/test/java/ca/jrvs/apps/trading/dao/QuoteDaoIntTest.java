package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Quote;
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
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;
    private final Quote savedQuote = new Quote();
    private final Quote savedQuote2 = new Quote();

    @Before
    public void setQuotes() {
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
    }

    @Test
    public void findByID() {
        Quote returnQuote = quoteDao.findById("AAPL").get();
        Quote returnQuote2 = quoteDao.findById("FB").get();

        assertEquals(savedQuote.getTicker(), returnQuote.getTicker());
        assertEquals(savedQuote2.getTicker(), returnQuote2.getTicker());
    }

    @Test
    public void saveOne() {
        Quote updateQuote = new Quote();
        updateQuote.setAskPrice(11d);
        updateQuote.setAskSize(11);
        updateQuote.setBidPrice(11.2d);
        updateQuote.setBidSize(11);
        updateQuote.setID("AAPL");
        updateQuote.setLastPrice(11.1d);

        quoteDao.save(updateQuote);

        assertNotEquals(updateQuote.getAskPrice(), savedQuote.getAskPrice());
    }

    @Test
    public void saveAll() {
        Quote savedQuote3 = new Quote();
        Quote savedQuote4 = new Quote();
        List<Quote> quoteList = new ArrayList();

        savedQuote3.setAskPrice(30d);
        savedQuote3.setAskSize(15);
        savedQuote3.setBidPrice(20.1d);
        savedQuote3.setBidSize(10);
        savedQuote3.setID("AMZN");
        savedQuote3.setLastPrice(11.2d);
        quoteList.add(savedQuote3);

        savedQuote4.setAskPrice(11d);
        savedQuote4.setAskSize(20);
        savedQuote4.setBidPrice(18.3d);
        savedQuote4.setBidSize(20);
        savedQuote4.setID("SMSG");
        savedQuote4.setLastPrice(14.5d);
        quoteList.add(savedQuote4);

        quoteDao.saveAll(quoteList);

        Quote returnQuote3 = quoteDao.findById("AMZN").get();
        Quote returnQuote4 = quoteDao.findById("SMSG").get();

        assertEquals(savedQuote3.getTicker(), returnQuote3.getTicker());
        assertEquals(savedQuote4.getTicker(), returnQuote4.getTicker());
    }

    @Test
    public void findAll() {
        List<Quote> quotes = quoteDao.findAll();
        Quote returnQuote = quotes.get(0);
        Quote returnQuote2 = quotes.get(1);

        assertEquals(savedQuote.getTicker(), returnQuote.getTicker());
        assertEquals(savedQuote2.getTicker(), returnQuote2.getTicker());
    }

    @Test
    public void existsById() {
        assertTrue(quoteDao.existsById("AAPL"));
        assertTrue(quoteDao.existsById("FB"));
    }

    @Test
    public void count() {
        assertEquals(2, quoteDao.count());
    }

    @After
    public void clearQuotes() {
        quoteDao.deleteById(savedQuote.getId());
        quoteDao.deleteById(savedQuote2.getId());
        quoteDao.deleteAll();
    }

}