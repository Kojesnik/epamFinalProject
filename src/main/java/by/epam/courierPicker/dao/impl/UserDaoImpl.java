package by.epam.courierPicker.dao.impl;

import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.dao.UserDao;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;
import by.epam.courierPicker.type.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public enum UserDaoImpl implements UserDao {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_CREATE_USER =
            "INSERT INTO courierPicker.user (login, password, email, firstname, lastname, role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_USER =
            "DELETE FROM courierPicker.user WHERE (firstname = ?) AND (lastname = ?) AND (email = ?) AND (password = ?) AND (role = ?) AND (login = ?)";
    private static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT * FROM courierPicker.user WHERE (login = ?)";
    private static final String SQL_SELECT_USER_BY_EMAIL =
            "SELECT * FROM courierPicker.user WHERE (email = ?)";
    private static final String SQL_SELECT_ID_BY_LOGIN =
            "SELECT id_user FROM courierPicker.user WHERE (login = ?)";
    private static final String SQL_SELECT_USER_BY_ID =
            "SELECT * FROM courierPicker.user WHERE (id_user = ?)";
    private static final String SQL_UPDATE_LOGIN_BY_ID =
            "UPDATE user SET login = ? WHERE (id_user = ?)";
    private static final String SQL_UPDATE_EMAIL_BY_ID =
            "UPDATE user SET email = ? WHERE (id_user = ?)";
    private static final String SQL_UPDATE_FIRSTNAME_BY_ID =
            "UPDATE user SET firstname = ? WHERE (id_user = ?)";
    private static final String SQL_UPDATE_LASTNAME_BY_ID =
            "UPDATE user SET lastname = ? WHERE (id_user = ?)";
    private static final String SQL_UPDATE_PASSWORD_BY_ID =
            "UPDATE user SET password = ? WHERE (id_user = ?)";

    @Override
    public boolean updateLoginById(String login, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_LOGIN_BY_ID);
            statement.setString(1, login);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_LOGIN_BY_ID + " completed");
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public boolean updateFirstNameById(String firstname, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_FIRSTNAME_BY_ID);
            statement.setString(1, firstname);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_FIRSTNAME_BY_ID + " completed");
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public boolean updateLastNameById(String lastname, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_LASTNAME_BY_ID);
            statement.setString(1, lastname);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_LASTNAME_BY_ID + " completed");
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public boolean updateEmailById(String email, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_EMAIL_BY_ID);
            statement.setString(1, email);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_EMAIL_BY_ID + " completed");
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public boolean updatePasswordById(String password, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_PASSWORD_BY_ID);
            statement.setString(1, password);
            statement.setInt(2, idUser);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_PASSWORD_BY_ID + " completed");
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public User findUserByLogin(String login) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = User.newBuilder().buildId(resultSet.getInt(1))
                        .buildLogin(resultSet.getString(2))
                        .buildPassword(resultSet.getString(3))
                        .buildEmail(resultSet.getString(4))
                        .buildFirstName(resultSet.getString(5))
                        .buildLastName(resultSet.getString(6))
                        .buildRole(RoleType.valueOf(resultSet.getString(7).toUpperCase()))
                        .build();
            }
            logger.info(SQL_SELECT_USER_BY_LOGIN + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return user;
        }
    }

    @Override
    public Integer findUserIdByLogin(String login) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID_BY_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            logger.info(SQL_SELECT_ID_BY_LOGIN + " completed");
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
    public User findUserByEmail(String email) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = User.newBuilder().buildId(resultSet.getInt(1))
                        .buildLogin(resultSet.getString(2))
                        .buildPassword(resultSet.getString(3))
                        .buildEmail(resultSet.getString(4))
                        .buildFirstName(resultSet.getString(5))
                        .buildLastName(resultSet.getString(6))
                        .buildRole(RoleType.valueOf(resultSet.getString(7).toUpperCase()))
                        .build();
            }
            logger.info(SQL_SELECT_USER_BY_EMAIL + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return user;
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findEntityById(Integer id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = User.newBuilder().buildId(resultSet.getInt(1))
                        .buildLogin(resultSet.getString(2))
                        .buildPassword(resultSet.getString(3))
                        .buildEmail(resultSet.getString(4))
                        .buildFirstName(resultSet.getString(5))
                        .buildLastName(resultSet.getString(6))
                        .buildRole(RoleType.valueOf(resultSet.getString(7).toUpperCase()))
                        .build();
            }
            logger.info(SQL_SELECT_USER_BY_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return user;
        }
    }

    @Override
    public boolean delete(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString().toLowerCase());
            statement.setString(6, user.getLogin());
            statement.executeUpdate();
            logger.info(SQL_DELETE_USER + " completed");
            return true;
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Integer create(User user) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, user.getRole().toString().toLowerCase());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            logger.info(SQL_CREATE_USER + " completed");
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
    public boolean update(User user) {
        return false;
    }

}
