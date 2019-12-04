package by.epam.courierPicker.logic;

import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.impl.CourierOfferDaoImpl;
import by.epam.courierPicker.dao.impl.GoodsDaoImpl;
import by.epam.courierPicker.dao.impl.TransportDaoImpl;
import by.epam.courierPicker.dao.impl.UserDaoImpl;
import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;
import by.epam.courierPicker.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CourierOfferLogic {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    public boolean addCourierOffer(String[] transportArray, String[] goodsArray, Integer idUser, String comment) throws LogicException {
        try {
            CourierOffer courierOffer = new CourierOffer(idUser, ParamName.STATUS_NOT_APPROVED_PARAM);
            courierOffer.setCourierComment(comment);
            Integer idOffer = CourierOfferDaoImpl.INSTANCE.create(courierOffer);
            for (String transport : transportArray) {
                CourierOfferDaoImpl.INSTANCE.addTransportToOffer(idOffer, TransportDaoImpl.INSTANCE.findIdByType(transport));
            }
            for (String goods : goodsArray) {
                CourierOfferDaoImpl.INSTANCE.addGoodsToOffer(idOffer, GoodsDaoImpl.INSTANCE.findIdByType(goods));
            }
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean acceptUserOffer(Integer idOffer, Integer idUser) throws LogicException {
        try {
            CourierOfferDaoImpl.INSTANCE.updateStatus(idOffer, idUser, ParamName.STATUS_ACCEPTED_PARAM, ParamName.STATUS_NOT_ACCEPTED_PARAM);
            CourierOfferDaoImpl.INSTANCE.updateAcceptDate(idOffer, idUser, ParamName.STATUS_ACCEPTED_PARAM, new Date(System.currentTimeMillis()));
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean approveCourierOffer(Integer idOffer) throws LogicException {
        try {
            CourierOfferDaoImpl.INSTANCE.updateOfferStatusById(idOffer, ParamName.STATUS_APPROVED_PARAM);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean deleteCourierOffer(Integer idOffer) throws LogicException {
        try {
            CourierOfferDaoImpl.INSTANCE.updateOfferStatusById(idOffer, ParamName.STATUS_DELETED_PARAM);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean finishCourierOffer(Integer idOffer, Integer idUser) throws LogicException{
        try {
            CourierOfferDaoImpl.INSTANCE.updateStatus(idOffer, idUser, ParamName.STATUS_FINISHED_PARAM, ParamName.STATUS_ACCEPTED_PARAM);
            CourierOfferDaoImpl.INSTANCE.updateFinishDate(idOffer, idUser, ParamName.STATUS_FINISHED_PARAM, new Date(System.currentTimeMillis()));
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean sendOfferToCourier(Integer idOffer, Integer idUser, String comment) throws LogicException {
        try {
            CourierOfferDaoImpl.INSTANCE.sendOfferToCourier(idOffer, idUser, comment);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map findApprovedCourierOffers(Integer idUser) throws LogicException {
        try {
            List<CourierOffer> courierOffers = CourierOfferDaoImpl.INSTANCE.findApprovedOffers();
            Map<User, CourierOffer> offers = new HashMap<>();
            for (CourierOffer courierOffer : courierOffers) {
                if (!CourierOfferDaoImpl.INSTANCE.find(courierOffer.getIdOffer(), idUser)) {
                    courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                    courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                    User user = UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser());
                    offers.put(user, courierOffer);
                }
            }
            return offers;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map<CourierOffer, Map<User, String>> findCourierOffers(Integer idUser) throws LogicException {
        try {
            Map<CourierOffer, Map<User, String>> offerMap = new HashMap();
            List<CourierOffer> offers = CourierOfferDaoImpl.INSTANCE.findOffersByUserId(idUser);
            for (CourierOffer courierOffer : offers) {
                if (!courierOffer.getStatus().equals(ParamName.STATUS_DELETED_PARAM)) {
                    courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                    courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                    offerMap.put(courierOffer, CourierOfferDaoImpl.INSTANCE.findNotAcceptedUsersByOfferId(courierOffer.getIdOffer()));
                }
            }
            return offerMap;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

}
