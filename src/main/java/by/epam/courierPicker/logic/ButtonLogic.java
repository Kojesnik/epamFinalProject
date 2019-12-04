package by.epam.courierPicker.logic;

import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ButtonName;
import by.epam.courierPicker.dao.impl.CourierOfferDaoImpl;
import by.epam.courierPicker.dao.impl.UserDaoImpl;
import by.epam.courierPicker.dao.impl.UserOfferDaoImpl;
import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.entity.UserOffer;
import by.epam.courierPicker.exception.DaoException;
import by.epam.courierPicker.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ButtonLogic {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    public Map adminButtons(String buttonValue) throws LogicException {
        try {
            switch (buttonValue) {
                case ButtonName.COURIER_OFFERS:
                    Map<CourierOffer, User> courierOfferMap = new HashMap();
                    List<CourierOffer> courierOffers = CourierOfferDaoImpl.INSTANCE.findNotApprovedOffers();
                    for (CourierOffer courierOffer : courierOffers) {
                        courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                        courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                        courierOfferMap.put(courierOffer, UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser()));
                    }
                    return courierOfferMap;
                case ButtonName.USER_OFFERS:
                    Map<UserOffer, User> userOfferMap = new HashMap();
                    List<UserOffer> userOffers = UserOfferDaoImpl.INSTANCE.findNotApprovedOffers();
                    for (UserOffer userOffer : userOffers) {
                        userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                        userOfferMap.put(userOffer, UserDaoImpl.INSTANCE.findEntityById(userOffer.getIdUser()));
                    }
                    return userOfferMap;
                default:
                    return null;
            }
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map<String, Map> courierButtons(Integer idUser) throws LogicException {
        try {
            Map<String, Map> req = new HashMap();
            Map<CourierOffer, User> activeOffersMap = new HashMap();
            Map<Map<UserOffer, List<User>>, User> activeUserOffersMap = new HashMap();
            List<CourierOffer> activeOffers = CourierOfferDaoImpl.INSTANCE.findAcceptedOffersByCourierId(idUser);
            List<UserOffer> activeUserOffers = UserOfferDaoImpl.INSTANCE.findOffersByCourierId(idUser);
            for (CourierOffer courierOffer : activeOffers) {
                courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                User user = UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser());
                activeOffersMap.put(courierOffer, user);
            }
            for (UserOffer userOffer: activeUserOffers) {
                if (userOffer.getNeededCourierNumber() == userOffer.getActiveCourierNumber()) {
                    List<User> couriers = new ArrayList<>();
                    userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                    for (User courier : UserOfferDaoImpl.INSTANCE.findCouriersByOfferId(userOffer.getIdOffer()).keySet()) {
                        if (courier.getId() != idUser) {
                            couriers.add(courier);
                        }
                    }
                    Map<UserOffer, List<User>> map = new HashMap();
                    map.put(userOffer, couriers);
                    activeUserOffersMap.put(map, UserDaoImpl.INSTANCE.findEntityById(userOffer.getIdUser()));
                }
            }
            req.put(AttributeName.ACTIVE_OFFERS, activeOffersMap);
            req.put(AttributeName.ACTIVE_OFFER_MAP, activeUserOffersMap);
            Map<CourierOffer, User> pastCourierOffersMap = new HashMap<>();
            Map<UserOffer, User> pastUserOffersMap = new HashMap<>();
            List<CourierOffer> pastCourierOffers = CourierOfferDaoImpl.INSTANCE.findFinishedOffersByCourierId(idUser);
            List<UserOffer> pastUserOffers = UserOfferDaoImpl.INSTANCE.findFinishedOffersByCourierId(idUser);
            for (CourierOffer courierOffer : pastCourierOffers) {
                courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                User user = UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser());
                pastCourierOffersMap.put(courierOffer, user);
            }
            for (UserOffer userOffer : pastUserOffers) {
                userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                User user = UserDaoImpl.INSTANCE.findEntityById(userOffer.getIdUser());
                pastUserOffersMap.put(userOffer, user);
            }
            req.put(AttributeName.PAST_OFFERS, pastCourierOffersMap);
            req.put(AttributeName.PAST_OFFER_MAP, pastUserOffersMap);
            return req;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map userButtons(String buttonValue, Integer idUser) throws LogicException {
        try {
            Map<String, Map> req = new HashMap();
            switch (buttonValue) {
                case ButtonName.USER_OFFERS:
                    Map<UserOffer, Map<User, String>> offerMap = new HashMap<>();
                    List<UserOffer> offers = UserOfferDaoImpl.INSTANCE.findOffersByUserId(idUser);
                    for (UserOffer userOffer : offers) {
                        userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                        offerMap.put(userOffer, UserOfferDaoImpl.INSTANCE.findNotAcceptedCouriersByOfferId(userOffer.getIdOffer()));
                    }
                    req.put(AttributeName.OFFER_MAP, offerMap);
                    return req;
                case ButtonName.ACTIVE_OFFERS:
                    Map<CourierOffer, User> activeCourierOffersMap = new HashMap<>();
                    Map<UserOffer, Map<User, String>> activeUserOffersMap = new HashMap<>();
                    List<CourierOffer> activeCourierOffers = CourierOfferDaoImpl.INSTANCE.findAcceptedOffersByUserId(idUser);
                    List<UserOffer> activeUserOffers = UserOfferDaoImpl.INSTANCE.findAcceptedOffersByUserId(idUser);
                    for (CourierOffer courierOffer : activeCourierOffers) {
                        courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                        courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                        User user = UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser());
                        activeCourierOffersMap.put(courierOffer, user);
                    }
                    for (UserOffer userOffer : activeUserOffers) {
                        userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                        activeUserOffersMap.put(userOffer, UserOfferDaoImpl.INSTANCE.findCouriersByOfferId(userOffer.getIdOffer()));
                    }
                    req.put(AttributeName.ACTIVE_OFFERS, activeCourierOffersMap);
                    req.put(AttributeName.OFFER_MAP, activeUserOffersMap);
                    return req;
                case ButtonName.PAST_OFFERS:
                    Map<CourierOffer, User> pastCourierOffersMap = new HashMap<>();
                    Map<UserOffer, User> pastUserOffersMap = new HashMap<>();
                    List<CourierOffer> pastCourierOffers = CourierOfferDaoImpl.INSTANCE.findFinishedOffersByUserId(idUser);
                    List<UserOffer> pastUserOffers = UserOfferDaoImpl.INSTANCE.findFinishedOffersByUserId(idUser);
                    for (CourierOffer courierOffer : pastCourierOffers) {
                        courierOffer.setTransport(CourierOfferDaoImpl.INSTANCE.findTransportByOfferId(courierOffer.getIdOffer()));
                        courierOffer.setGoods(CourierOfferDaoImpl.INSTANCE.findGoodsByOfferId(courierOffer.getIdOffer()));
                        User user = UserDaoImpl.INSTANCE.findEntityById(courierOffer.getIdUser());
                        pastCourierOffersMap.put(courierOffer, user);
                    }
                    for (UserOffer userOffer : pastUserOffers) {
                        userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                        User user = UserDaoImpl.INSTANCE.findEntityById(userOffer.getIdUser());
                        pastUserOffersMap.put(userOffer, user);
                    }
                    req.put(AttributeName.PAST_OFFERS, pastCourierOffersMap);
                    req.put(AttributeName.OFFER_MAP, pastUserOffersMap);
                    return req;
                default:
                    return null;
            }
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

}
