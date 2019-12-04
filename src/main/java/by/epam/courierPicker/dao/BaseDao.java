package by.epam.courierPicker.dao;

import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.entity.Entity;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BaseDao <K, T extends Entity> {

    Logger logger = LogManager.getLogger();

    List<T> findAll() throws DaoException;
    T findEntityById(K id) throws DaoException;
    boolean delete(T t) throws DaoException;
    boolean delete(K id) throws DaoException;
    Integer create(T t) throws DaoException;
    boolean update(T t) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    default void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
