package by.epam.courierPicker.dao.impl;

import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.dao.GoodsDao;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public enum GoodsDaoImpl implements GoodsDao {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_SELECT_ID_BY_TYPE =
            "SELECT id_goods FROM courierPicker.goods WHERE (type = ?)";

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
            return id;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public List<Goods> findAll() {
        return null;
    }

    @Override
    public Goods findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Goods goods) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Integer create(Goods goods) {
        return 0;
    }

    @Override
    public boolean update(Goods goods) {
        return false;
    }
}
