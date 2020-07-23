package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Quote;
import ca.jrvs.apps.trading.model.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

    private final String TABLE_NAME = "trader";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public TraderDao(DataSource dataSource) {
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
    Class<Trader> getEntityClass() {
        return Trader.class;
    }

    /**
     * Helper method for updating existing trader
     *
     * @param entity trader
     */
    @Override
    public int updateOne(Trader entity) {
        String updateSQL = "UPDATE trader SET first_name=?, last_name=?, email=?, " +
                "dob=? WHERE id=?";
        return jdbcTemplate.update(updateSQL, makeUpdateValues(entity));
    }

    /**
     * Helper method that makes sql update values into objects
     *
     * @param entity trader that is to be updated
     * @throws DataRetrievalFailureException if trader is not found
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Trader entity) {
        if (!existsById(entity.getId())) {
            throw new DataRetrievalFailureException("Trader not found:" + entity.getId());
        }

        Object[] values = {entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                            entity.getDob(), entity.getId()};
        return values;

    }

}
