package by.epam.courierPicker.dao.impl;

import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.UserOfferDao;
import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.entity.UserOffer;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UserOfferDaoImpl implements UserOfferDao {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_CREATE_OFFER =
            "INSERT INTO courierPicker.user_offer (id_user, needed_couriers_number, active_couriers_number, status, comment) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_ADD_GOODS_TO_USER_OFFER =
            "INSERT INTO courierPicker.user_offer_goods (id_offer, id_goods) VALUES (?, ?)";
    private static final String SQL_SELECT_GOODS_BY_OFFER_ID =
            "SELECT type FROM courierPicker.user_offer_goods INNER JOIN goods USING(id_goods) WHERE (id_offer = ?)";
    private static final String SQL_SELECT_OFFERS_BY_STATUS =
            "SELECT * FROM courierPicker.user_offer WHERE (status = ?)";
    private static final String SQL_UPDATE_STATUS_BY_ID =
            "UPDATE courierPicker.user_offer SET status = ? WHERE (id_offer = ?)";
    private static final String SQL_SELECT_OFFERS_BY_USER_ID =
            "SELECT user_offer.id_offer,user_offer.id_user,needed_couriers_number,active_couriers_number,user_offer.status,user_offer.comment,user_offer_courier.comment FROM courierPicker.user_offer LEFT JOIN user_offer_courier USING (id_offer) WHERE (user_offer.id_user = ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT status FROM courierPicker.user_offer_courier WHERE (id_offer = ?) AND (id_user = ?)";
    private static final String SQL_SEND_OFFER =
            "INSERT INTO courierPicker.user_offer_courier (id_offer, id_user, status, comment) VALUES (?, ?, ?,?)";
    private static final String SQL_SELECT_USERS_BY_OFFER_ID =
            "SELECT id_user, login, email, firstname, lastname, accept_date, comment FROM courierPicker.user_offer_courier INNER JOIN user USING(id_user) WHERE (id_offer = ?) AND (status = ?)";
    private static final String SQL_DELETE_OFFER_BY_ID =
            "DELETE FROM courierPicker.user_offer WHERE (id_offer = ?)";
    private static final String SQL_UPDATE_STATUS =
            "UPDATE courierPicker.user_offer_courier SET status = ?  WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";
    private static final String SQL_UPDATE_ACCEPT_DATE =
            "UPDATE courierPicker.user_offer_courier SET accept_date = ? WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";
    private static final String SQL_UPDATE_FINISH_DATE =
            "UPDATE user_offer_courier SET finish_date = ? WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";
    private static final String SQL_UPDATE_ACTIVE_COURIER_NUMBER =
            "UPDATE courierPicker.user_offer SET active_couriers_number = ?  WHERE (id_offer = ?)";
    private static final String SQL_SELECT_ACTIVE_COURIER_NUMBER_BY_OFFER_ID =
            "SELECT active_couriers_number FROM user_offer WHERE (id_offer = ?)";
    private static final String SQL_SELECT_NEEDED_COURIER_NUMBER_BY_OFFER_ID =
            "SELECT needed_couriers_number FROM user_offer WHERE (id_offer = ?)";
    private static final String SQL_SELECT_ACCEPTED_OFFERS_BY_USER_ID =
            "SELECT id_offer, needed_couriers_number, active_couriers_number, accept_date,user_offer.comment,user_offer_courier.comment FROM user_offer INNER JOIN user_offer_courier USING (id_offer) WHERE (user_offer.id_user = ?) AND (user_offer_courier.status = ?)";
    private static final String SQL_SELECT_COURIERS_BY_OFFER_ID =
            "SELECT id_user, login, email, firstname, lastname, comment FROM user INNER JOIN user_offer_courier USING (id_user) WHERE (id_offer = ?) AND (status = ?)";
    private static final String SQL_SELECT_OFFERS_BY_COURIER_ID =
            "SELECT id_offer,user_offer.id_user,needed_couriers_number,active_couriers_number,accept_date,user_offer_courier.comment,user_offer.comment FROM user_offer INNER JOIN user_offer_courier USING (id_offer) WHERE (user_offer_courier.id_user = ?) AND (user_offer_courier.status = ?) AND (user_offer.status != ?)";
    private static final String SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS =
            "SELECT id_offer, user_offer_courier.id_user, accept_date,finish_date,user_offer.comment,user_offer_courier.comment FROM user_offer INNER JOIN user_offer_courier USING (id_offer) WHERE (user_offer.id_user = ?) AND (user_offer_courier.status = ?)";
    private static final String SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS =
            "SELECT id_offer,user_offer.id_user,accept_date,finish_date,user_offer.comment,user_offer_courier.comment FROM user_offer INNER JOIN user_offer_courier USING (id_offer) WHERE (user_offer_courier.id_user = ?) AND (user_offer_courier.status = ?)";

    @Override
    public List<UserOffer> findFinishedOffersByCourierId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> userOffers = null;
        try {
            userOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_FINISHED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setStartDate(resultSet.getDate(3));
                userOffer.setEndDate(resultSet.getDate(4));
                userOffer.setUserComment(resultSet.getString(5));
                userOffer.setCourierComment(resultSet.getString(6));
                userOffers.add(userOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return userOffers;
        }
    }

    @Override
    public List<UserOffer> findFinishedOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> userOffers = null;
        try {
            userOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_FINISHED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setStartDate(resultSet.getDate(3));
                userOffer.setEndDate(resultSet.getDate(4));
                userOffer.setUserComment(resultSet.getString(5));
                userOffer.setCourierComment(resultSet.getString(6));
                userOffers.add(userOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return userOffers;
        }
    }

    @Override
    public List<UserOffer> findOffersByCourierId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> offers = null;
        try {
            offers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_COURIER_ID);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_ACCEPTED_PARAM);
            statement.setString(3, ParamName.STATUS_DELETED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setNeededCourierNumber(resultSet.getInt(3));
                userOffer.setActiveCourierNumber(resultSet.getInt(4));
                userOffer.setStartDate(resultSet.getDate(5));
                userOffer.setCourierComment(resultSet.getString(6));
                userOffer.setUserComment(resultSet.getString(7));
                offers.add(userOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_COURIER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return offers;
        }
    }

    @Override
    public Map<User, String> findCouriersByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<User, String> users = null;
        try {
            users = new HashMap();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_COURIERS_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            statement.setString(2, ParamName.STATUS_ACCEPTED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = User.newBuilder().buildId(resultSet.getInt(1))
                        .buildLogin(resultSet.getString(2))
                        .buildEmail(resultSet.getString(3))
                        .buildFirstName(resultSet.getString(4))
                        .buildLastName(resultSet.getString(5))
                        .build();
                users.put(user, resultSet.getString(6));
            }
            logger.info(SQL_SELECT_COURIERS_BY_OFFER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return users;
        }
    }

    @Override
    public List<UserOffer> findAcceptedOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> offers = null;
        List<Integer> id = null;
        try {
            offers = new ArrayList<>();
            id = new ArrayList<>();
            boolean isUnique;
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ACCEPTED_OFFERS_BY_USER_ID);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_ACCEPTED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                isUnique = true;
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setNeededCourierNumber(resultSet.getInt(2));
                userOffer.setActiveCourierNumber(resultSet.getInt(3));
                userOffer.setStartDate(resultSet.getDate(4));
                userOffer.setUserComment(resultSet.getString(5));
                userOffer.setCourierComment(resultSet.getString(6));
                for (Integer idOffer: id) {
                    if (idOffer == userOffer.getIdOffer()) {
                        isUnique = false;
                    }
                }
                if (isUnique && userOffer.getActiveCourierNumber() == userOffer.getNeededCourierNumber()) {
                    id.add(userOffer.getIdOffer());
                    offers.add(userOffer);
                }
            }
            logger.info(SQL_SELECT_ACCEPTED_OFFERS_BY_USER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return offers;
        }
    }

    @Override
    public Integer findNeededCourierNumberByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer number = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_NEEDED_COURIER_NUMBER_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt(1);
            }
            logger.info(SQL_SELECT_NEEDED_COURIER_NUMBER_BY_OFFER_ID + " completed");
            return number;
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
    public Integer findActiveCourierNumberByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer number = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ACTIVE_COURIER_NUMBER_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt(1);
            }
            logger.info(SQL_SELECT_ACTIVE_COURIER_NUMBER_BY_OFFER_ID + " completed");
            return number;
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
    public boolean updateActiveCourierNumber(Integer idOffer, Integer courierNumber) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_ACTIVE_COURIER_NUMBER);
            statement.setInt(1, courierNumber);
            statement.setInt(2, idOffer);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_ACTIVE_COURIER_NUMBER + " completed");
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
    public boolean updateFinishDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_FINISH_DATE);
            statement.setDate(1, date);
            statement.setInt(2, idOffer);
            statement.setInt(3, idUser);
            statement.setString(4, status);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_FINISH_DATE + " completed");
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
    public boolean updateAcceptDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_ACCEPT_DATE);
            statement.setDate(1, date);
            statement.setInt(2, idOffer);
            statement.setInt(3, idUser);
            statement.setString(4, status);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_ACCEPT_DATE + " completed");
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
    public boolean updateStatus(Integer idOffer, Integer idUser, String futureStatus, String pastStatus) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATUS);
            statement.setString(1, futureStatus);
            statement.setInt(2, idOffer);
            statement.setInt(3, idUser);
            statement.setString(4, pastStatus);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_STATUS + " completed");
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
    public Map<User, String> findNotAcceptedCouriersByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<User, String> users = null;
        try {
            users = new HashMap();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_USERS_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            statement.setString(2, ParamName.STATUS_NOT_ACCEPTED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = User.newBuilder().buildId(resultSet.getInt(1))
                        .buildLogin(resultSet.getString(2))
                        .buildEmail(resultSet.getString(3))
                        .buildFirstName(resultSet.getString(4))
                        .buildLastName(resultSet.getString(5))
                        .build();
                users.put(user, resultSet.getString(7));
            }
            logger.info(SQL_SELECT_USERS_BY_OFFER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return users;
        }
    }

    @Override
    public boolean sendOfferToUser(Integer idOffer, Integer idUser, String comment) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SEND_OFFER);
            statement.setInt(1, idOffer);
            statement.setInt(2, idUser);
            statement.setString(3, ParamName.STATUS_NOT_ACCEPTED_PARAM);
            statement.setString(4, comment);
            statement.executeUpdate();
            logger.info(SQL_SEND_OFFER + " completed");
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
    public UserOffer find(Integer idOffer, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        UserOffer userOffer = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, idOffer);
            statement.setInt(2, idUser);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userOffer = new UserOffer();
                userOffer.setStatus(resultSet.getString(1));
            }
            logger.info(SQL_SELECT_BY_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);

            return userOffer;
        }
    }

    @Override
    public List<UserOffer> findOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> userOffers = null;
        List<Integer> id = null;
        try {
            id = new ArrayList();
            userOffers = new ArrayList();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_USER_ID);
            statement.setInt(1, idUser);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                boolean isUnique = true;
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setNeededCourierNumber(resultSet.getInt(3));
                userOffer.setActiveCourierNumber(resultSet.getInt(4));
                userOffer.setStatus(resultSet.getString(5));
                userOffer.setUserComment(resultSet.getString(6));
                userOffer.setCourierComment(resultSet.getString(7));
                for (Integer idOffer: id) {
                    if (idOffer == userOffer.getIdOffer()) {
                        isUnique = false;
                    }
                }
                if (isUnique) {
                    id.add(userOffer.getIdOffer());
                    userOffers.add(userOffer);
                }
            }
            logger.info(SQL_SELECT_OFFERS_BY_USER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return userOffers;
        }
    }

    @Override
    public boolean updateOfferStatusById(Integer idOffer, String status) throws DaoException  {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID);
            statement.setString(1, status);
            statement.setInt(2, idOffer);
            statement.executeUpdate();
            logger.info(SQL_UPDATE_STATUS_BY_ID + " completed");
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
    public List<UserOffer> findApprovedOffers() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> userOffers = null;
        try {
            userOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_STATUS);
            statement.setString(1, ParamName.STATUS_APPROVED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setNeededCourierNumber(resultSet.getInt(3));
                userOffer.setActiveCourierNumber(resultSet.getInt(4));
                userOffer.setUserComment(resultSet.getString(6));
                userOffers.add(userOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return userOffers;
        }
    }

    @Override
    public List<UserOffer> findNotApprovedOffers() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<UserOffer> userOffers = null;
        try {
            userOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_STATUS);
            statement.setString(1, ParamName.STATUS_NOT_APPROVED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserOffer userOffer = new UserOffer();
                userOffer.setIdOffer(resultSet.getInt(1));
                userOffer.setIdUser(resultSet.getInt(2));
                userOffer.setNeededCourierNumber(resultSet.getInt(3));
                userOffer.setUserComment(resultSet.getString(6));
                userOffers.add(userOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return userOffers;
        }
    }

    @Override
    public List<Goods> findGoodsByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Goods> goods = null;
        try {
            goods = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_GOODS_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Goods goods1 = new Goods();
                goods1.setType(resultSet.getString(1));
                goods.add(goods1);
            }
            logger.info(SQL_SELECT_GOODS_BY_OFFER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return goods;
        }
    }

    @Override
    public boolean addGoodsToUserOffer(Integer idOffer, Integer idGoods) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_ADD_GOODS_TO_USER_OFFER);
            statement.setInt(1, idOffer);
            statement.setInt(2, idGoods);
            statement.executeUpdate();
            logger.info(SQL_ADD_GOODS_TO_USER_OFFER + " completed");
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
    public List<UserOffer> findAll() throws DaoException {
        return null;
    }

    @Override
    public UserOffer findEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(UserOffer userOffer) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_OFFER_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
            logger.info(SQL_DELETE_OFFER_BY_ID + " completed");
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
    public Integer create(UserOffer userOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_CREATE_OFFER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userOffer.getIdUser());
            statement.setInt(2, userOffer.getNeededCourierNumber());
            statement.setInt(3, 0);
            statement.setString(4, userOffer.getStatus());
            statement.setString(5, userOffer.getUserComment());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            logger.info(SQL_CREATE_OFFER + " completed");
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
    public boolean update(UserOffer userOffer) throws DaoException {
        return false;
    }
}
