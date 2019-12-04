package by.epam.courierPicker.dao;

import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.entity.UserOffer;
import by.epam.courierPicker.exception.DaoException;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface UserOfferDao extends BaseDao<Integer, UserOffer> {

    boolean addGoodsToUserOffer(Integer idOffer, Integer idGoods) throws DaoException;
    List<Goods> findGoodsByOfferId(Integer idOffer) throws DaoException;
    List<UserOffer> findNotApprovedOffers() throws DaoException;
    boolean updateOfferStatusById(Integer idOffer, String status) throws DaoException;
    List<UserOffer> findOffersByUserId(Integer idUser) throws DaoException;
    List<UserOffer> findApprovedOffers() throws DaoException;
    UserOffer find(Integer idOffer, Integer idUser) throws DaoException;
    boolean sendOfferToUser(Integer idOffer, Integer idUser, String comment) throws DaoException;
    Map<User, String> findNotAcceptedCouriersByOfferId(Integer idOffer) throws DaoException;
    List<UserOffer> findAcceptedOffersByUserId(Integer idUser) throws DaoException;
    boolean updateStatus(Integer idOffer, Integer idUser, String futureStatus, String pastStatus) throws DaoException;
    boolean updateAcceptDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException;
    boolean updateFinishDate(Integer idOffer, Integer idUser, String status, Date date) throws DaoException;
    boolean updateActiveCourierNumber(Integer idOffer, Integer courierNumber) throws DaoException;
    Integer findActiveCourierNumberByOfferId(Integer idOffer) throws DaoException;
    Integer findNeededCourierNumberByOfferId(Integer idOffer) throws DaoException;
    Map<User, String> findCouriersByOfferId(Integer idOffer) throws DaoException;
    List<UserOffer> findOffersByCourierId(Integer idUser) throws DaoException;
    List<UserOffer> findFinishedOffersByUserId(Integer idUser) throws DaoException;
    List<UserOffer> findFinishedOffersByCourierId(Integer idUser) throws DaoException;

}
