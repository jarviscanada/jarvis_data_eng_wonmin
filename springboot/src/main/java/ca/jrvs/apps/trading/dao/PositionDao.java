package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Position;
import ca.jrvs.apps.trading.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "account_id";
    private final String TICKER_COLUMN = "ticker";

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Position> findById(Integer id) {
        List<Position> entity = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=?";

        try {
            entity = jdbcTemplate.query (selectSQL, BeanPropertyRowMapper.newInstance(Position.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader ID" + id, e);
        }

        return entity;
    }

    public Optional<Position> findByIdAndTicker(Integer id, String ticker) {
        Optional<Position> entity = null;
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE (" + ID_COLUMN+ "=? AND "
                + TICKER_COLUMN + "=?)";
        try {
            entity = Optional.of(jdbcTemplate
                    .queryForObject(selectSQL, BeanPropertyRowMapper.newInstance(Position.class), ticker));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Can't find the specified position", e);
        }

        if (entity == null) {
            return Optional.empty();
        }
        return entity;
    }

    public boolean existsById(Integer id) {
        List<Position> positions = findById(id);

        if (!positions.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public List<Position> findAll() {
        List<Position> entityList;
        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        entityList = getJdbcTemplate().query(selectSQL, BeanPropertyRowMapper.newInstance(Position.class));

        return entityList;
    }

    public long count() {
        String countSQL = "SELECT COUNT(*) FROM " + TABLE_NAME;
        long count = 0;
        try {
            count = getJdbcTemplate().queryForObject(countSQL, Long.class);
        } catch (NullPointerException e) {
            logger.debug("Unexpected count operation");
        }
        return count;
    }

}
