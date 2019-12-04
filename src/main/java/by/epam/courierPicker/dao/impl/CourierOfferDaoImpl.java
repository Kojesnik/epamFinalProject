package by.epam.courierPicker.dao.impl;

import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.connectionpool.ProxyConnection;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.CourierOfferDao;
import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.entity.Transport;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CourierOfferDaoImpl implements CourierOfferDao {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_CREATE_OFFER =
            "INSERT INTO courierPicker.courier_offer (id_user, status, comment) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_OFFERS_BY_USER_ID =
            "SELECT id_offer,courier_offer.status,courier_offer.comment,courier_offer_user.comment FROM courierPicker.courier_offer LEFT JOIN courier_offer_user USING (id_offer) WHERE (courier_offer.id_user = ?)";
    private static final String SQL_SELECT_OFFERS_BY_STATUS =
            "SELECT id_offer,id_user,comment FROM courierPicker.courier_offer WHERE (status = ?)";
    private static final String SQL_DELETE_OFFER_BY_ID =
            "DELETE FROM courierPicker.courier_offer WHERE (id_offer = ?)";
    private static final String SQL_UPDATE_STATUS_BY_ID =
            "UPDATE courierPicker.courier_offer SET status = ? WHERE (id_offer = ?)";
    private static final String SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS =
            "SELECT id_offer,courier_offer_user.id_user,accept_date,finish_date,courier_offer.comment,courier_offer_user.comment FROM courier_offer INNER JOIN courier_offer_user USING (id_offer) WHERE (courier_offer.id_user = ?) AND (courier_offer_user.status = ?)";
    private static final String SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS =
            "SELECT id_offer, courier_offer.id_user, accept_date,finish_date,courier_offer_user.comment,courier_offer.comment FROM courier_offer INNER JOIN courier_offer_user USING (id_offer) WHERE (courier_offer_user.id_user = ?) AND (courier_offer_user.status = ?)";
    private static final String SQL_ADD_GOODS_TO_OFFER =
            "INSERT INTO courierPicker.courier_offer_goods (id_offer, id_goods) VALUES (?, ?)";
    private static final String SQL_SELECT_GOODS_BY_OFFER_ID =
            "SELECT type FROM courierPicker.courier_offer_goods INNER JOIN goods USING(id_goods) WHERE (id_offer = ?)";
    private static final String SQL_ADD_TRANSPORT_TO_OFFER =
            "INSERT INTO courierPicker.courier_offer_transport (id_offer, id_transport) VALUES (?, ?)";
    private static final String SQL_SELECT_TRANSPORT_BY_OFFER_ID =
            "SELECT type FROM courierPicker.courier_offer_transport INNER JOIN transport USING(id_transport) WHERE (id_offer = ?)";
    private static final String SQL_INSERT_OFFER =
            "INSERT INTO courierPicker.courier_offer_user (id_offer, id_user, status, comment) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_USERS_BY_OFFER_ID =
            "SELECT id_user, login, email, firstname, lastname, accept_date, comment FROM courierPicker.courier_offer_user INNER JOIN user USING(id_user) WHERE (id_offer = ?) AND (status = ?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM courierPicker.courier_offer_user WHERE (id_offer = ?) AND (id_user = ?)";
    private static final String SQL_UPDATE_STATUS =
            "UPDATE courierPicker.courier_offer_user SET status = ?  WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";
    private static final String SQL_UPDATE_ACCEPT_DATE =
            "UPDATE courierPicker.courier_offer_user SET accept_date = ? WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";
    private static final String SQL_UPDATE_FINISH_DATE =
            "UPDATE courier_offer_user SET finish_date = ? WHERE (id_offer = ?) AND (id_user = ?) AND (status = ?)";

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
    public boolean find(Integer idOffer, Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, idOffer);
            statement.setInt(2, idUser);
            resultSet = statement.executeQuery();
            logger.info(SQL_SELECT_BY_ID + " completed");
            return resultSet.next();
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
    public Map<User, String> findNotAcceptedUsersByOfferId(Integer idOffer) throws DaoException {
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
    public boolean sendOfferToCourier(Integer idOffer, Integer idUser, String comment) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_INSERT_OFFER);
            statement.setInt(1, idOffer);
            statement.setInt(2, idUser);
            statement.setString(3, ParamName.STATUS_NOT_ACCEPTED_PARAM);
            statement.setString(4, comment);
            statement.executeUpdate();
            logger.info(SQL_INSERT_OFFER + " completed");
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
    public List<Transport> findTransportByOfferId(Integer idOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Transport> transport = null;
        try {
            transport = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_TRANSPORT_BY_OFFER_ID);
            statement.setInt(1, idOffer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Transport transport1 = new Transport();
                transport1.setType(resultSet.getString(1));
                transport.add(transport1);
            }
            logger.info(SQL_SELECT_TRANSPORT_BY_OFFER_ID + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return transport;
        }
    }

    @Override
    public boolean addTransportToOffer(Integer idOffer, Integer idTransport) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_ADD_TRANSPORT_TO_OFFER);
            statement.setInt(1, idOffer);
            statement.setInt(2, idTransport);
            statement.executeUpdate();
            logger.info(SQL_ADD_TRANSPORT_TO_OFFER + " completed");
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
    public boolean addGoodsToOffer(Integer idOffer, Integer idGoods) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_ADD_GOODS_TO_OFFER);
            statement.setInt(1, idOffer);
            statement.setInt(2, idGoods);
            statement.executeUpdate();
            logger.info(SQL_ADD_GOODS_TO_OFFER + " completed");
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
    public List<CourierOffer> findFinishedOffersByCourierId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_FINISHED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setStartDate(resultSet.getDate(3));
                courierOffer.setEndDate(resultSet.getDate(4));
                courierOffer.setCourierComment(resultSet.getString(5));
                courierOffer.setUserComment(resultSet.getString(6));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findFinishedOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_FINISHED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setStartDate(resultSet.getDate(3));
                courierOffer.setEndDate(resultSet.getDate(4));
                courierOffer.setUserComment(resultSet.getString(5));
                courierOffer.setCourierComment(resultSet.getString(6));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findAcceptedOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_ACCEPTED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setStartDate(resultSet.getDate(3));
                courierOffer.setUserComment(resultSet.getString(5));
                courierOffer.setCourierComment(resultSet.getString(6));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_USER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findAcceptedOffersByCourierId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS);
            statement.setInt(1, idUser);
            statement.setString(2, ParamName.STATUS_ACCEPTED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setStartDate(resultSet.getDate(3));
                courierOffer.setCourierComment(resultSet.getString(5));
                courierOffer.setUserComment(resultSet.getString(6));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_COURIER_ID_AND_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findApprovedOffers() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_STATUS);
            statement.setString(1, ParamName.STATUS_APPROVED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setCourierComment(resultSet.getString(3));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
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
    public List<CourierOffer> findNotApprovedOffers() throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        try {
            courierOffers = new ArrayList<>();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_STATUS);
            statement.setString(1, ParamName.STATUS_NOT_APPROVED_PARAM);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourierOffer courierOffer = new CourierOffer(resultSet.getInt(2), ParamName.STATUS_NOT_APPROVED_PARAM);
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setCourierComment(resultSet.getString(3));
                courierOffers.add(courierOffer);
            }
            logger.info(SQL_SELECT_OFFERS_BY_STATUS + " completed");
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
            throw new DaoException(ex.getMessage());
        } finally {
            close(statement);
            close(resultSet);
            ConnectionPool.INSTANCE.releaseConnection(connection);
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findOffersByUserId(Integer idUser) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<CourierOffer> courierOffers = null;
        List<Integer> id;
        try {
            id = new ArrayList();
            courierOffers = new ArrayList();
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_OFFERS_BY_USER_ID);
            statement.setInt(1, idUser);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                boolean isUnique = true;
                CourierOffer courierOffer = new CourierOffer(idUser, resultSet.getString(2));
                courierOffer.setIdOffer(resultSet.getInt(1));
                courierOffer.setCourierComment(resultSet.getString(3));
                courierOffer.setUserComment(resultSet.getString(4));
                for (Integer idOffer: id) {
                    if (idOffer == courierOffer.getIdOffer()) {
                        isUnique = false;
                    }
                }
                if (isUnique) {
                    id.add(courierOffer.getIdOffer());
                    courierOffers.add(courierOffer);
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
            return courierOffers;
        }
    }

    @Override
    public List<CourierOffer> findAll() {
        return null;
    }

    @Override
    public CourierOffer findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(CourierOffer courierOffer) {
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
    public Integer create(CourierOffer courierOffer) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer id = 0;
        try {
            connection = ConnectionPool.INSTANCE.getConnection();
            statement = connection.prepareStatement(SQL_CREATE_OFFER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, courierOffer.getIdUser());
            statement.setString(2, courierOffer.getStatus());
            statement.setString(3, courierOffer.getCourierComment());
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
    public boolean update(CourierOffer courierOffer) {
        return false;
    }
}
