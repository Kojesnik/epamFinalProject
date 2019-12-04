package by.epam.courierPicker.logic;

import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.impl.*;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.entity.UserOffer;
import by.epam.courierPicker.exception.DaoException;
import by.epam.courierPicker.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum UserOfferLogic {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    public boolean addUserOffer(String[] goodsArray, Integer idUser, Integer neededCourierNumber, String comment) throws LogicException {
        try {
            UserOffer userOffer = new UserOffer();
            userOffer.setIdUser(idUser);
            userOffer.setStatus(ParamName.STATUS_NOT_APPROVED_PARAM);
            userOffer.setNeededCourierNumber(neededCourierNumber);
            userOffer.setUserComment(comment);
            Integer idOffer = UserOfferDaoImpl.INSTANCE.create(userOffer);
            for (String goods : goodsArray) {
                UserOfferDaoImpl.INSTANCE.addGoodsToUserOffer(idOffer, GoodsDaoImpl.INSTANCE.findIdByType(goods));
            }
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean acceptCourierOffer(Integer idOffer, Integer idUser) throws LogicException {
        try {
            UserOfferDaoImpl.INSTANCE.updateStatus(idOffer, idUser, ParamName.STATUS_ACCEPTED_PARAM, ParamName.STATUS_NOT_ACCEPTED_PARAM);
            UserOfferDaoImpl.INSTANCE.updateActiveCourierNumber(idOffer, UserOfferDaoImpl.INSTANCE.findActiveCourierNumberByOfferId(idOffer) + 1);
            if (UserOfferDaoImpl.INSTANCE.findNeededCourierNumberByOfferId(idOffer) == UserOfferDaoImpl.INSTANCE.findActiveCourierNumberByOfferId(idOffer)) {
                for (User courier: UserOfferDaoImpl.INSTANCE.findCouriersByOfferId(idOffer).keySet()) {
                    UserOfferDaoImpl.INSTANCE.updateAcceptDate(idOffer, courier.getId(), ParamName.STATUS_ACCEPTED_PARAM, new Date(System.currentTimeMillis()));
                }
            }
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean approveUserOffer(Integer idOffer) throws LogicException {
        try {
            UserOfferDaoImpl.INSTANCE.updateOfferStatusById(idOffer, ParamName.STATUS_APPROVED_PARAM);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean deleteUserOffer(Integer idOffer) throws LogicException {
        try {
            UserOfferDaoImpl.INSTANCE.updateOfferStatusById(idOffer, ParamName.STATUS_DELETED_PARAM);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean sendOfferToUser(Integer idOffer, Integer idUser, String comment) throws LogicException {
        try {
            UserOfferDaoImpl.INSTANCE.sendOfferToUser(idOffer, idUser, comment);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map findApprovedUserOffers(Integer idUser) throws LogicException {
        try {
            List<UserOffer> userOffers = UserOfferDaoImpl.INSTANCE.findApprovedOffers();
            Map<UserOffer, User> offers = new HashMap<>();
            for (UserOffer userOffer : userOffers) {
                if ((UserOfferDaoImpl.INSTANCE.find(userOffer.getIdOffer(), idUser) == null || UserOfferDaoImpl.INSTANCE.find(userOffer.getIdOffer(), idUser).getStatus().equals(ParamName.STATUS_FINISHED_PARAM))
                        && UserOfferDaoImpl.INSTANCE.findActiveCourierNumberByOfferId(userOffer.getIdOffer()) != UserOfferDaoImpl.INSTANCE.findNeededCourierNumberByOfferId(userOffer.getIdOffer())) {
                    userOffer.setGoods(UserOfferDaoImpl.INSTANCE.findGoodsByOfferId(userOffer.getIdOffer()));
                    User user = UserDaoImpl.INSTANCE.findEntityById(userOffer.getIdUser());
                    offers.put(userOffer, user);
                }
            }
            return offers;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean finishUserOffer(Integer idOffer, Integer idUser, String role) throws LogicException{
        try {
            if (role.equals(ParamName.USER_PARAM)) {
                for (User courier : UserOfferDaoImpl.INSTANCE.findCouriersByOfferId(idOffer).keySet()) {
                    UserOfferDaoImpl.INSTANCE.updateStatus(idOffer, courier.getId(), ParamName.STATUS_FINISHED_PARAM, ParamName.STATUS_ACCEPTED_PARAM);
                    UserOfferDaoImpl.INSTANCE.updateFinishDate(idOffer, courier.getId(), ParamName.STATUS_FINISHED_PARAM, new Date(System.currentTimeMillis()));
                    UserOfferDaoImpl.INSTANCE.updateActiveCourierNumber(idOffer, 0);
                }
            } else if (role.equals(ParamName.COURIER_PARAM)) {
                UserOfferDaoImpl.INSTANCE.updateStatus(idOffer, idUser, ParamName.STATUS_FINISHED_PARAM, ParamName.STATUS_ACCEPTED_PARAM);
                UserOfferDaoImpl.INSTANCE.updateFinishDate(idOffer, idUser, ParamName.STATUS_FINISHED_PARAM, new Date(System.currentTimeMillis()));
                UserOfferDaoImpl.INSTANCE.updateActiveCourierNumber(idOffer, UserOfferDaoImpl.INSTANCE.findActiveCourierNumberByOfferId(idOffer) - 1);
            }
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }
}
