package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    /**
     * Updates ticker if already exists, or inserts a new ticker
     * @param quote
     * @throws DataRetrievalFailureException for unexpected SQL result or SQL exception failure
     * @return retrieved quote model
     */
    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updateRowNo = updateOne(quote);
            if (updateRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * Helper method for adding a single ticker
     * @throws IncorrectResultSizeDataAccessException if the row size is too big
     * @param quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * Helper method for updating existing ticker
     * @param quote
     */
    private int updateOne(Quote quote) {
        String updateSQL = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, " +
                "ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(updateSQL, makeUpdateValues(quote));
    }

    /**
     * Helper method that makes sql update values into objects
     * @param quote that is to be updated
     * @throws DataRetrievalFailureException if quote is not found
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote) {
        if (!existsById(quote.getTicker())) {
            throw new DataRetrievalFailureException("Ticker not found:" + quote.getTicker());
        }

        Object[] values = {quote.getLastPrice(), quote.getBidPrice(), quote.getBidSize(),
                            quote.getAskPrice(), quote.getAskSize(), quote.getTicker()};
        return values;

    }

    /**
     * Update multiple tickers
     * @param quotes
     * @param <S>
     * @return
     */
    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<S> quoteList = new ArrayList<>();
        quotes.forEach(quote -> {
            quoteList.add((S) save(quote));
        });
        return quoteList;
    }

    /**
     * Find a quote by ticker
     * @param ticker
     * @return quote or Optional.empty if not found
     */
    @Override
    public Optional<Quote> findById(String ticker) {
        Optional<Quote> quote = null;
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        try {
            quote = Optional.of(jdbcTemplate
                    .queryForObject(selectSQL, BeanPropertyRowMapper.newInstance(Quote.class), ticker));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Can't find trader id: " + ticker, e);
        }
        if (quote == null) {
            return Optional.empty();
        }
        return quote;
    }

    /**
     * Return all quotes
     * @throws DataAccessException if failed to update
     * @return list containing all quotes
     */
    @Override
    public List<Quote> findAll() {
        List<Quote> quotes = new ArrayList<>();
        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        quotes = jdbcTemplate.query(selectSQL, BeanPropertyRowMapper.newInstance(Quote.class));

        return quotes;
    }

    @Override
    public boolean existsById(String ticker) {
        Optional<Quote> quote = findById(ticker);

        if (quote.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long count() {
        String countSQL = "SELECT COUNT(*) FROM " + TABLE_NAME;
        long count = jdbcTemplate.queryForObject(countSQL, Long.class);

        return count;
    }

    @Override
    public void deleteAll() {
        String deleteAllSQL = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(deleteAllSQL);
    }

    @Override
    public void deleteById(String ticker) {
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";
        jdbcTemplate.update(deleteSQL, ticker);
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
