package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.CourierOfferLogic;
import by.epam.courierPicker.type.RoleType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class FinishCourierOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("FinishCourierOfferCommand executed");
            Integer idOffer = Integer.parseInt(request.getParameter(ParamName.ID_OFFER_PARAM));
            Integer idUser;
            if (request.getSession().getAttribute(ParamName.ROLE_PARAM).equals(RoleType.COURIER.toString().toLowerCase())) {
                idUser = Integer.parseInt(request.getParameter(ParamName.ID_USER_PARAM));
                CourierOfferLogic.INSTANCE.finishCourierOffer(idOffer, idUser);
                request.setAttribute(AttributeName.REDIRECT, "?command=courier_button&button=active_offers");
            } else if (request.getSession().getAttribute(ParamName.ROLE_PARAM).equals(RoleType.USER.toString().toLowerCase())) {
                idUser = Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString());
                CourierOfferLogic.INSTANCE.finishCourierOffer(idOffer, idUser);
                request.setAttribute(AttributeName.REDIRECT, "?command=user_button&button=active_offers");
            }
            logger.info("Redirect to active offers");
            return "";
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
