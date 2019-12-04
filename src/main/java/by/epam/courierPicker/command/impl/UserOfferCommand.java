package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserOfferLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("UserOfferCommand executed");
            String page = Path.USER_PAGE_PATH;
            UserOfferLogic.INSTANCE.addUserOffer(request.getParameterValues(ParamName.PACKAGE_PARAM), Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()), Integer.parseInt(request.getParameter(ParamName.NEEDED_COURIER_NUMBER_PARAM)), request.getParameter(ParamName.COMMENT_PARAM));
            request.setAttribute(AttributeName.REDIRECT, "?command=account_command");
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage() );
            throw new CommandException(ex.getMessage());
        }
    }
}
