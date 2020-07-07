package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Account;
import ca.jrvs.apps.trading.model.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
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
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    /**
     * Helper method for updating existing security order
     *
     * @param entity security order
     */
    @Override
    public int updateOne(SecurityOrder entity) {
        String updateSQL = "UPDATE security_order SET account_id=?, status=?, ticker=?, " +
                "size=?, price=?, notes=? WHERE id=?";
        return jdbcTemplate.update(updateSQL, makeUpdateValues(entity));
    }

    /**
     * Helper method that makes sql update values into objects
     *
     * @param entity security that is to be updated
     * @throws DataRetrievalFailureException if security order is not found
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(SecurityOrder entity) {
        if (!existsById(entity.getId())) {
            throw new DataRetrievalFailureException("Trader not found:" + entity.getId());
        }

        Object[] values = {entity.getAccountId(), entity.getStatus(), entity.getTicker(),
        entity.getSize(), entity.getPrice(), entity.getNotes(), entity.getId()};

        return values;
    }
}
