package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.ButtonLogic;
import by.epam.courierPicker.logic.CourierOfferLogic;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class AccountCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("AccountCommand executed");
            String page;
            User user = UserLogic.INSTANCE.findUser(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            request.setAttribute(ParamName.LOGIN_PARAM, user.getLogin());
            request.setAttribute(ParamName.EMAIL_PARAM, user.getEmail());
            request.setAttribute(ParamName.FIRSTNAME_PARAM, user.getFirstName());
            request.setAttribute(ParamName.LASTNAME_PARAM, user.getLastName());
            switch (request.getSession().getAttribute(ParamName.ROLE_PARAM).toString()) {
                case ParamName.USER_PARAM:
                    page = Path.USER_PAGE_PATH;
                    break;
                case ParamName.ADMIN_PARAM:
                    page = Path.ADMIN_PAGE_PATH;
                    break;
                case ParamName.COURIER_PARAM:
                    page = Path.COURIER_PAGE_PATH;
                    Map req = ButtonLogic.INSTANCE.courierButtons(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
                    request.setAttribute(AttributeName.ACTIVE_OFFERS, req.get(AttributeName.ACTIVE_OFFERS));
                    request.setAttribute(AttributeName.ACTIVE_OFFER_MAP, req.get(AttributeName.ACTIVE_OFFER_MAP));
                    request.setAttribute(AttributeName.PAST_OFFERS, req.get(AttributeName.PAST_OFFERS));
                    request.setAttribute(AttributeName.PAST_OFFER_MAP, req.get(AttributeName.PAST_OFFER_MAP));
                    request.setAttribute(AttributeName.OFFER_MAP, CourierOfferLogic.INSTANCE.findCourierOffers(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString())));
                    break;
                default:
                    page = Path.MAIN_PATH;
            }
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
