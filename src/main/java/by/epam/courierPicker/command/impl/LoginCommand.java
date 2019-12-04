package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ErrorName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LoginCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("LoginCommand executed");
            String page;
            Map req = UserLogic.INSTANCE.loginUser(request.getParameter(ParamName.PASSWORD_PARAM), request.getParameter(ParamName.LOGIN_PARAM));
            if (req.get(AttributeName.ERROR_LOGIN_PASS) == null) {
                request.getSession().setAttribute(ParamName.ROLE_PARAM, req.get(ParamName.ROLE_PARAM));
                request.getSession().setAttribute(ParamName.STATUS_PARAM, req.get(ParamName.STATUS_PARAM));
                request.getSession().setAttribute(ParamName.ID_USER_PARAM, req.get(ParamName.ID_USER_PARAM));
                page = Path.MAIN_PATH;
            } else {
                request.setAttribute(AttributeName.ERROR_LOGIN_PASS, ErrorName.LOGIN_ERROR);
                page = Path.LOGIN_PATH;
            }
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }

}