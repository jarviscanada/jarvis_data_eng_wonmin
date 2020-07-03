package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;
    private final Trader savedTrader = new Trader();
    private final Trader savedTrader2 = new Trader();

    @Before
    public void setTraders() {
        savedTrader.setFirstName("Robert");
        savedTrader.setLastName("DeNiro");
        savedTrader.setCountry("USA");
        savedTrader.setDob(Date.valueOf("1943-08-17"));
        savedTrader.setEmail("deNiro@thisemail.com");
        savedTrader.setID(1);

        savedTrader2.setFirstName("Jack");
        savedTrader2.setLastName("Nicholson");
        savedTrader2.setCountry("USA");
        savedTrader2.setDob(Date.valueOf("1937-04-22"));
        savedTrader2.setEmail("Nicholson@thisemail.com");
        savedTrader2.setID(2);

        traderDao.save(savedTrader);
        traderDao.save(savedTrader2);
    }

    @Test
    public void findById() {
        Trader returnTrader = traderDao.findById(savedTrader.getId()).get();

        assertNotNull(returnTrader);
        assertEquals(savedTrader.getEmail(), returnTrader.getEmail());
        assertEquals(savedTrader.getLastName(), returnTrader.getLastName());
    }

    @Test
    public void existsById() {
        assertTrue(traderDao.existsById(savedTrader.getId()));
    }

    @Test
    public void findAll() {
        List<Trader> traders = traderDao.findAll();

        assertEquals(2, traders.size());
        assertEquals(savedTrader.getLastName(), traders.get(0).getLastName());
        assertEquals(savedTrader2.getEmail(), traders.get(1).getEmail());
    }

    @Test
    public void findAllById() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1); idList.add(2);

        List<Trader> traders  = traderDao.findAllById(idList);

        assertEquals(2, traders.size());
        assertEquals(savedTrader.getLastName(), traders.get(0).getLastName());
        assertEquals(savedTrader2.getEmail(), traders.get(1).getEmail());
    }

    @Test
    public void count() {
        assertEquals(2, traderDao.count());
    }

    @After
    public void clearTraders() {
        traderDao.deleteById(savedTrader.getId());
        traderDao.deleteById(savedTrader2.getId());
        traderDao.deleteAll();
    }
}