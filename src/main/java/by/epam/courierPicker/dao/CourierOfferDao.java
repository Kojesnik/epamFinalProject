package by.epam.courierPicker.dao;

import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.entity.Transport;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface CourierOfferDao extends BaseDao<Integer, CourierOffer>{

    List<CourierOffer> findOffersByUserId(Integer idUser) throws DaoException;
    List<CourierOffer> findNotApprovedOffers() throws DaoException;
    List<CourierOffer> findApprovedOffers() throws DaoException;
    boolean updateOfferStatusById(Integer idOffer, String status) throws DaoException;
    List<CourierOffer> findAcceptedOffersByCourierId(Integer idUser) throws DaoException;
    List<CourierOffer> findFinishedOffersByCourierId(Integer idUser) throws DaoException;
    List<CourierOffer> findFinishedOffersByUserId(Integer idUser) throws DaoException;
    List<CourierOffer> findAcceptedOffersByUserId(Integer idUser) throws DaoException;
    boolean addGoodsToOffer(Integer idOffer, Integer idGoods) throws DaoException;
    List<Goods> findGoodsByOfferId(Integer idOffer) throws DaoException;
    boolean addTransportToOffer(Integer idOffer, Integer idTransport) throws DaoException;
    List<Transport> findTransportByOfferId(Integer idOffer) throws DaoException;
    boolean sendOfferToCourier(Integer idOffer, Integer idUser, String comment) throws DaoException;
    Map<User, String> findNotAcceptedUsersByOfferId(Integer idOffer) throws DaoException;
    boolean find(Integer idOffer, Integer idUser) throws DaoException;
    boolean updateStatus(Integer idOffer, Integer idUser, String futureStatus, String pastStatus) throws DaoException;
    boolean updateAcceptDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException;
    boolean updateFinishDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException;

}
