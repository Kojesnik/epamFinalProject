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

public class AdminButtonCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("AdminButtonCommand executed");
            String page = Path.ADMIN_PAGE_PATH;
            request.setAttribute(AttributeName.OFFER_MAP, ButtonLogic.INSTANCE.adminButtons(request.getParameter(ParamName.BUTTON_PARAM)));
            switch (request.getParameter(ParamName.BUTTON_PARAM)) {
                case ButtonName.COURIER_OFFERS:
                    request.setAttribute(ButtonName.COURIER_OFFERS, true);
                    break;
                case ButtonName.USER_OFFERS:
                    request.setAttribute(ButtonName.USER_OFFERS, true);
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
