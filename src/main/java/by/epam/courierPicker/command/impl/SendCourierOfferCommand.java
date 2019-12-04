package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserOfferLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SendCourierOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("SendCourierOfferCommand executed");
            UserOfferLogic.INSTANCE.sendOfferToUser(Integer.parseInt(request.getParameter(ParamName.ID_OFFER_PARAM)), Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()), request.getParameter(ParamName.COMMENT_PARAM));
            request.setAttribute(AttributeName.REDIRECT, "?command=show_approved_user_offers");
            logger.info("Redirect to approved user offers");
            return "";
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
