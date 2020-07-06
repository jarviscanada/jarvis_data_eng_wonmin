package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    @Mock
    private AccountDao accountDao;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private PositionDao positionDao;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void executeBuyOrder() {
        MarketOrderDto orderDto = new MarketOrderDto();
        orderDto.setAccountId(1);
        orderDto.setSize(10);
        orderDto.setTicker("AAPL");

        Account account = new Account();
        account.setID(1);
        account.setTraderId(1);
        account.setAmount(100D);

        Quote quote = new Quote();
        quote.setAskPrice(10D);

        OrderService spyService = Mockito.spy(orderService);

        //Fail path
        try {
            spyService.handleBuyMarketOrder(any(), any(), any());
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //Success path
        doReturn(Optional.of(account)).when(accountDao).findById(any());
        doReturn(Optional.of(quote)).when(quoteDao).findById(any());

        SecurityOrder actualOrder = spyService.executeMarketOrder(orderDto);

        Assert.assertNotNull(actualOrder);
        Assert.assertEquals(quote.getAskPrice(), actualOrder.getPrice());
        Assert.assertEquals(orderDto.getSize(), actualOrder.getSize());
    }

    @Test
    public void executeSellOrder() {
        MarketOrderDto orderDto = new MarketOrderDto();
        orderDto.setAccountId(1);
        orderDto.setSize(-20);
        orderDto.setTicker("AAPL");

        Account account = new Account();
        account.setID(1);
        account.setTraderId(1);
        account.setAmount(100D);

        Quote quote = new Quote();
        quote.setBidPrice(10D);

        Position savedPosition = new Position();
        savedPosition.setAccountId(1);
        savedPosition.setTicker(orderDto.getTicker());
        savedPosition.setPosition(20);

        OrderService spyService = Mockito.spy(orderService);

        //Fail path
        try {
            spyService.handleSellMarketOrder(any(), any(), any());
            fail();
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        //Success path
        doReturn(Optional.of(account)).when(accountDao).findById(any());
        doReturn(Optional.of(quote)).when(quoteDao).findById(any());
        doReturn(Optional.of(savedPosition)).when(positionDao).findByIdAndTicker(any(), any());

        SecurityOrder actualOrder = spyService.executeMarketOrder(orderDto);

        Assert.assertNotNull(actualOrder);
        Assert.assertEquals(orderDto.getSize(), actualOrder.getSize());
    }

}