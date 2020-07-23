package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.IexQuote;
import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoIntTest {

    private MarketDataDao dao;

    @Before
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTickers() throws IOException {
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));

        assertEquals(2, quoteList.size());
        assertEquals("AAPL", quoteList.get(0).getSymbol());
        assertEquals("FB", quoteList.get(1).getSymbol());
    }

    @Test
    public void findByTicker() {
        IexQuote iexQuote = dao.findById("AAPL").get();

        assertEquals("AAPL", iexQuote.getSymbol());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failSave() {
        IexQuote iexQuote = dao.findById("AAPL").get();
        dao.save(iexQuote);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failExistsById() {
        dao.existsById("AAPL");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failFindAll() {
        dao.findAll();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failCount() {
        dao.count();
    }

}