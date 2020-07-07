package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.SecurityOrder;
import ca.jrvs.apps.trading.model.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;
    private final Quote savedQuote = new Quote();
    private final Quote savedQuote2 = new Quote();

    @Autowired
    private TraderDao traderDao;
    private final Trader savedTrader = new Trader();
    private final Trader savedTrader2 = new Trader();

    @Autowired
    private AccountDao accountDao;
    private final Account savedAccount = new Account();
    private final Account savedAccount2 = new Account();

    @Autowired
    private SecurityOrderDao securityOrderDao;
    private final SecurityOrder savedOrder = new SecurityOrder();
    private final SecurityOrder savedOrder2 = new SecurityOrder();

    @Before
    public void setSecurityOrders() {

        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setID("AAPL");
        savedQuote.setLastPrice(10.1d);

        savedTrader.setFirstName("Robert");
        savedTrader.setLastName("DeNiro");
        savedTrader.setCountry("USA");
        savedTrader.setDob(Date.valueOf("1943-08-17"));
        savedTrader.setEmail("deNiro@thisemail.com");
        savedTrader.setID(1);

        savedAccount.setID(1);
        savedAccount.setTraderId(1);
        savedAccount.setAmount(100.53D);

        savedOrder.setID(1);
        savedOrder.setAccountId(1);
        savedOrder.setNotes("Update");
        savedOrder.setPrice(100D);
        savedOrder.setSize(10);
        savedOrder.setStatus("PENDING");
        savedOrder.setTicker("AAPL");

        savedQuote2.setAskPrice(20d);
        savedQuote2.setAskSize(13);
        savedQuote2.setBidPrice(12.2d);
        savedQuote2.setBidSize(30);
        savedQuote2.setID("FB");
        savedQuote2.setLastPrice(12.5d);

        savedTrader2.setFirstName("Jack");
        savedTrader2.setLastName("Nicholson");
        savedTrader2.setCountry("USA");
        savedTrader2.setDob(Date.valueOf("1937-04-22"));
        savedTrader2.setEmail("Nicholson@thisemail.com");
        savedTrader2.setID(2);

        savedAccount2.setID(2);
        savedAccount2.setTraderId(2);
        savedAccount2.setAmount(200.15D);

        savedOrder2.setID(2);
        savedOrder2.setAccountId(2);
        savedOrder2.setNotes("Update");
        savedOrder2.setPrice(200D);
        savedOrder2.setSize(20);
        savedOrder2.setStatus("SOLD");
        savedOrder2.setTicker("FB");

        quoteDao.save(savedQuote);
        quoteDao.save(savedQuote2);
        traderDao.save(savedTrader);
        traderDao.save(savedTrader2);
        accountDao.save(savedAccount);
        accountDao.save(savedAccount2);
        securityOrderDao.save(savedOrder);
        securityOrderDao.save(savedOrder2);
    }

    @Test
    public void findById() {
        SecurityOrder returnOrder = securityOrderDao.findById(savedOrder.getId()).get();

        assertNotNull(returnOrder);
        assertEquals(savedOrder.getPrice(), returnOrder.getPrice());
        assertEquals(savedOrder.getStatus(), returnOrder.getStatus());
    }

    @Test
    public void existsById() {
        assertTrue(securityOrderDao.existsById(savedOrder.getId()));
    }

    @Test
    public void findAll() {
        List<SecurityOrder> orders = securityOrderDao.findAll();

        assertEquals(2, orders.size());
        assertEquals(savedOrder.getPrice(), orders.get(0).getPrice());
        assertEquals(savedOrder2.getNotes(), orders.get(1).getNotes());
    }

    @Test
    public void findAllById() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1); idList.add(2);

        List<SecurityOrder> orders = securityOrderDao.findAllById(idList);

        assertEquals(2, orders.size());
        assertEquals(savedOrder.getPrice(), orders.get(0).getPrice());
        assertEquals(savedOrder2.getNotes(), orders.get(1).getNotes());
    }

    @Test
    public void count() {
        assertEquals(2, securityOrderDao.count());
    }

    @After
    public void clearSecurityOrders() {
        securityOrderDao.deleteById(savedOrder.getId());
        securityOrderDao.deleteById(savedOrder2.getId());
        securityOrderDao.deleteAll();
    }

}