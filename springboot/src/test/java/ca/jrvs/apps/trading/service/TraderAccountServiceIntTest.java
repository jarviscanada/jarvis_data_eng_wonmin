package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Trader;
import ca.jrvs.apps.trading.model.TraderAccountView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;
    @Autowired
    private TraderAccountService traderAccountService;
    @Autowired
    private TraderDao traderDao;
    private final Trader savedTrader = new Trader();
    @Autowired
    private AccountDao accountDao;

    @Before
    public void setTrader() {
        savedTrader.setFirstName("Robert");
        savedTrader.setLastName("DeNiro");
        savedTrader.setCountry("USA");
        savedTrader.setDob(Date.valueOf("1943-08-17"));
        savedTrader.setEmail("deNiro@thisemail.com");
        savedTrader.setID(1);

        traderDao.save(savedTrader);
        savedView = traderAccountService.createTraderAndAccount(savedTrader);
    }

    @Test
    public void createTraderAndAccount() {
        savedView = traderAccountService.createTraderAndAccount(savedTrader);

        assertEquals(savedTrader.getFirstName(), savedView.getTrader().getFirstName());
        assertEquals(savedTrader.getEmail(), savedView.getTrader().getEmail());
        assertEquals(Optional.of(0D).get(), savedView.getAccount().getAmount());
    }

    @Test
    public void deposit() {
        Account depositAccount = traderAccountService.deposit(savedTrader.getId(), 200D);
        savedView.setAccount(depositAccount);

        assertEquals(Optional.of(200D).get(), savedView.getAccount().getAmount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void failDepositByFund() { traderAccountService.deposit(savedTrader.getId(), -100D); }

    @Test
    public void withdraw() {
        Account depositAccount = traderAccountService.deposit(savedTrader.getId(), 200D);
        savedView.setAccount(depositAccount);
        Account withdrawAcount = traderAccountService.withdraw(savedTrader.getId(), 100D);
        savedView.setAccount(withdrawAcount);

        assertEquals(Optional.of(100D).get(), withdrawAcount.getAmount());
    }

    @Test (expected = IllegalArgumentException.class)
    public void failWithdrawByFund() { traderAccountService.deposit(savedTrader.getId(), 0D); }

    @Test
    public void deleteTraderById() {
        Account depositAccount = traderAccountService.deposit(savedTrader.getId(), 200D);
        savedView.setAccount(depositAccount);
        Account withdrawAcount = traderAccountService.withdraw(savedTrader.getId(), 200D);
        savedView.setAccount(withdrawAcount);

        traderAccountService.deleteTraderById(savedView.getAccount().getId());
    }

}