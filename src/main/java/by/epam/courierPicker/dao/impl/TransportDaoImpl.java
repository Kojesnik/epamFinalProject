package by.epam.courierPicker.dao.impl;

import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.dao.TransportDao;
import by.epam.courierPicker.entity.Transport;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public enum TransportDaoImpl implements TransportDao {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_ID_BY_TYPE =
            "SELECT id_transport FROM courierPicker.transport WHERE (type = ?)";

    @Override
    public Integer findIdByType(String type) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID_BY_TYPE);
            statement.setString(1, type);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            logger.info(SQL_SELECT_ID_BY_TYPE + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return id;
        }
    }

    @Override
    public List<Transport> findAll() {
        return null;
    }

    @Override
    public Transport findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Transport transport) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Integer create(Transport transport) {
        return 0;
    }

    @Override
    public boolean update(Transport transport) {
        return false;
    }
}
