package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("RegisterCommand executed");
            String page;
            Map req = UserLogic.INSTANCE.registerUser(request.getParameter(ParamName.PASSWORD_PARAM), request.getParameter(ParamName.ROLE_PARAM), request.getParameter(ParamName.EMAIL_PARAM), request.getParameter(ParamName.LOGIN_PARAM), request.getParameter(ParamName.FIRSTNAME_PARAM), request.getParameter(ParamName.LASTNAME_PARAM));
            if (req.get(ParamName.ID_USER_PARAM) != null) {
                page = Path.MAIN_PATH;
                request.getSession().setAttribute(ParamName.ROLE_PARAM, req.get(ParamName.ROLE_PARAM));
                request.getSession().setAttribute(ParamName.STATUS_PARAM, req.get(ParamName.STATUS_PARAM));
                request.getSession().setAttribute(ParamName.ID_USER_PARAM, req.get(ParamName.ID_USER_PARAM));
            } else {
                request.setAttribute(AttributeName.ERROR_LOGIN_REGISTER, req.get(AttributeName.ERROR_LOGIN_REGISTER));
                request.setAttribute(AttributeName.ERROR_EMAIL_REGISTER, req.get(AttributeName.ERROR_EMAIL_REGISTER));
                request.setAttribute(AttributeName.ERROR_PASSWORD_REGISTER, req.get(AttributeName.ERROR_PASSWORD_REGISTER));
                request.setAttribute(AttributeName.ERROR_FIRSTNAME_REGISTER, req.get(AttributeName.ERROR_FIRSTNAME_REGISTER));
                request.setAttribute(AttributeName.ERROR_LASTNAME_REGISTER, req.get(AttributeName.ERROR_LASTNAME_REGISTER));
                page = Path.SIGNUP_PATH;
            }
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
