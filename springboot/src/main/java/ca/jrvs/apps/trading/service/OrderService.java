package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     *
     * - validate the order (e.g. size and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     *     - buy order : check account balance (calls helper method)
     *     - sell order: check position for the ticker/symbol (calls helper method)
     * - Save and return securityOrder
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {

        if (orderDto.getTicker() == null || orderDto.getSize() == 0) {
            throw new IllegalArgumentException("Invalid ticker or size");
        }

        Account account = accountDao.findById(orderDto.getAccountId()).get();

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(orderDto.getAccountId());
        securityOrder.setID(orderDto.getAccountId());
        securityOrder.setSize(orderDto.getSize());
        securityOrder.setTicker(orderDto.getTicker());
        securityOrder.setStatus("PENDING");

        if (orderDto.getSize() > 0) {
            handleBuyMarketOrder(orderDto, securityOrder, account);
        } else if (orderDto.getSize() < 0) {
            handleSellMarketOrder(orderDto, securityOrder, account);
        }

        securityOrderDao.save(securityOrder);
        return securityOrder;
    }

    /**
     * Helper method that execute buy order
     *
     * @param orderDto user order
     * @param securityOrder to be saved in database
     * @param account account
     */
    protected void handleBuyMarketOrder(MarketOrderDto orderDto, SecurityOrder securityOrder,
    Account account) {
        Quote quote = quoteDao.findById(orderDto.getTicker()).get();

        Double buyPrice = quote.getAskPrice() * orderDto.getSize();
        if (buyPrice > account.getAmount()) {
            throw new IllegalArgumentException("Not enough funds in account to process buy order");
        }

        account.setAmount(account.getAmount() - buyPrice);
        securityOrder.setPrice(quote.getAskPrice());
        securityOrder.setStatus("FILLED");
        accountDao.save(account);
        securityOrderDao.save(securityOrder);
    }

    /**
     * Helper method that execute a sell order
     *
     * @param orderDto user order
     * @param securityOrder to be saved in database
     * @param account account
     */
    protected void handleSellMarketOrder(MarketOrderDto orderDto, SecurityOrder securityOrder,
    Account account) {
        Quote quote = quoteDao.findById(orderDto.getTicker()).get();
        Position position = positionDao.findByIdAndTicker(orderDto.getAccountId(), orderDto.getTicker()).get();

        if (position.getPosition() < orderDto.getSize()) {
            throw new IllegalArgumentException("Order size exceeds existing position");
        }

        Double sellPrice = quote.getBidPrice() * Math.abs(orderDto.getSize());

        account.setAmount(account.getAmount() + sellPrice);
        securityOrder.setPrice(sellPrice);
        securityOrder.setStatus("FILLED");
        accountDao.updateOne(account);
        securityOrderDao.save(securityOrder);
    }

}
