package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {

    @Autowired
    private AccountDao accountDao;
    private final Account savedAccount = new Account();
    private final Account savedAccount2 = new Account();

    @Autowired
    private TraderDao traderDao;
    private final Trader savedTrader = new Trader();
    private final Trader savedTrader2 = new Trader();

    @Before
    public void setAccounts() {

        savedTrader.setFirstName("Robert");
        savedTrader.setLastName("DeNiro");
        savedTrader.setCountry("USA");
        savedTrader.setDob(Date.valueOf("1943-08-17"));
        savedTrader.setEmail("deNiro@thisemail.com");
        savedTrader.setID(1);

        savedAccount.setID(1);
        savedAccount.setTraderId(1);
        savedAccount.setAmount(100.53D);

        savedTrader2.setFirstName("Jack");
        savedTrader2.setLastName("Nicholson");
        savedTrader2.setCountry("USA");
        savedTrader2.setDob(Date.valueOf("1937-04-22"));
        savedTrader2.setEmail("Nicholson@thisemail.com");
        savedTrader2.setID(2);

        savedAccount2.setID(2);
        savedAccount2.setTraderId(2);
        savedAccount2.setAmount(200.15D);

        traderDao.save(savedTrader);
        traderDao.save(savedTrader2);
        accountDao.save(savedAccount);
        accountDao.save(savedAccount2);
    }

    @Test
    public void findById() {
        Account returnAccount = accountDao.findById(savedAccount.getId()).get();

        assertNotNull(returnAccount);
        assertEquals(savedAccount.getTraderId(), returnAccount.getTraderId());
        assertEquals(savedAccount.getAmount(), returnAccount.getAmount());
    }

    @Test
    public void existsById() {
        assertTrue(accountDao.existsById(savedAccount.getId()));
    }

    @Test
    public void findAll() {
        List<Account> accounts = accountDao.findAll();

        assertEquals(2, accounts.size());
        assertEquals(savedAccount.getTraderId(), accounts.get(0).getTraderId());
        assertEquals(savedAccount2.getAmount(), accounts.get(1).getAmount());
    }

    @Test
    public void findAllById() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1); idList.add(2);

        List<Account> accounts = accountDao.findAllById(idList);

        assertEquals(2, accounts.size());
        assertEquals(savedAccount.getTraderId(), accounts.get(0).getTraderId());
        assertEquals(savedAccount2.getAmount(), accounts.get(1).getAmount());
    }

    @Test
    public void count() {
        assertEquals(2, accountDao.count());
    }

    @After
    public void clearAccounts() {
        accountDao.deleteById(savedAccount.getId());
        accountDao.deleteById(savedAccount2.getId());
        accountDao.deleteAll();

        traderDao.deleteById(savedTrader.getId());
        traderDao.deleteById(savedTrader2.getId());
        traderDao.deleteAll();
    }

}