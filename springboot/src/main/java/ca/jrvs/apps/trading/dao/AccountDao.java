package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.Entity;
import ca.jrvs.apps.trading.model.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class AccountDao extends JdbcCrudDao<Account>{

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    /**
     * Helper method for updating existing account
     *
     * @param entity account
     */
    @Override
    public int updateOne(Account entity) {
        String updateSQL = "UPDATE account SET trader_id=?, amount=? WHERE id=?";
        return jdbcTemplate.update(updateSQL, makeUpdateValues(entity));
    }

    /**
     * Helper method that makes sql update values into objects
     *
     * @param entity account that is to be updated
     * @throws DataRetrievalFailureException if account is not found
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Account entity) {
        if (!existsById(entity.getId())) {
            throw new DataRetrievalFailureException("Trader not found:" + entity.getId());
        }

        Object[] values = {entity.getTraderId(), entity.getAmount(), entity.getId()};

        return values;
    }

}
