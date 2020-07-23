package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdColumnName();

    abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     *
     * @param entity to be saved
     * @return save entity
     */
    @Override
    public <S extends T> S save(S entity) {
        if (existsById(entity.getId())) {
            if (updateOne(entity) != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(entity);
        }
        return entity;
    }

    /**
     * Helper method that saves one quote
     */
    private <S extends T> void addOne(S entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);

        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setID(newId.intValue());
    }

    /**
     * Helper method that updates one quote
     */
    abstract public int updateOne(T entity);


    @Override
    public Optional<T> findById(Integer id) {
        Optional<T> entity = Optional.empty();
        String selectSQL = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";

        try {
            entity = Optional.ofNullable((T) getJdbcTemplate()
                    .queryForObject(selectSQL, BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader ID" + id, e);
        }
        return entity;
    }

    @Override
    public boolean existsById(Integer id) {
        Optional<T> entity = findById(id);

        if (entity.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        List<T> entityList;
        String selectSQL = "SELECT * FROM " + getTableName();

        entityList = getJdbcTemplate().query(selectSQL, BeanPropertyRowMapper.newInstance(getEntityClass()));

        return entityList;
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {
        List<T> entityList = new ArrayList<>();

        ids.forEach(id -> {
            entityList.add(findById(id).get());
        });

        return entityList;
    }

    @Override
    public long count() {
        String countSQL = "SELECT COUNT(*) FROM " + getTableName();
        long count = 0;
        try {
            count = getJdbcTemplate().queryForObject(countSQL, Long.class);
        } catch (NullPointerException e) {
            logger.debug("Unexpected count operation");
        }
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteSQL = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + "=?";
        getJdbcTemplate().update(deleteSQL, id);
    }

    @Override
    public void deleteAll() {
        String deleteAllSQL = "DELETE FROM " + getTableName();
        getJdbcTemplate().update(deleteAllSQL);

    }

    @Override
    public void delete(T t) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
