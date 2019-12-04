package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ButtonName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.ButtonLogic;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UserButtonCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("UserButtonCommand executed");
            String page = Path.USER_PAGE_PATH;
            Map req = ButtonLogic.INSTANCE.userButtons(request.getParameter(ParamName.BUTTON_PARAM), Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            switch (request.getParameter(ParamName.BUTTON_PARAM)) {
                case ButtonName.USER_OFFERS:
                    request.setAttribute(ButtonName.USER_OFFERS, true);
                    request.setAttribute(AttributeName.OFFER_MAP, req.get(AttributeName.OFFER_MAP));
                    break;
                case ButtonName.ACTIVE_OFFERS:
                    request.setAttribute(ButtonName.ACTIVE_OFFERS, true);
                    request.setAttribute(AttributeName.ACTIVE_OFFERS, req.get(AttributeName.ACTIVE_OFFERS));
                    request.setAttribute(AttributeName.OFFER_MAP, req.get(AttributeName.OFFER_MAP));
                    break;
                case ButtonName.PAST_OFFERS:
                    request.setAttribute(ButtonName.PAST_OFFERS, true);
                    request.setAttribute(AttributeName.PAST_OFFERS, req.get(AttributeName.PAST_OFFERS));
                    request.setAttribute(AttributeName.OFFER_MAP, req.get(AttributeName.OFFER_MAP));
                    break;
                case ButtonName.SEND_USER_OFFER:
                    request.setAttribute(ButtonName.SEND_USER_OFFER, true);
                    break;
            }
            User user = UserLogic.INSTANCE.findUser(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            request.setAttribute(ParamName.LOGIN_PARAM, user.getLogin());
            request.setAttribute(ParamName.EMAIL_PARAM, user.getEmail());
            request.setAttribute(ParamName.FIRSTNAME_PARAM, user.getFirstName());
            request.setAttribute(ParamName.LASTNAME_PARAM, user.getLastName());
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
