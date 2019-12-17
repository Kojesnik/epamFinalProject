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
import java.util.Collections;
import java.util.TreeMap;

public class AcceptCourierOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    /**
     *
     * @param request
     * @return
     * @throws CommandException
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("AcceptUserOfferCommand executed");
            UserOfferLogic.INSTANCE.acceptCourierOffer(Integer.parseInt(request.getParameter(ParamName.ID_OFFER_PARAM)), Integer.parseInt(request.getParameter(ParamName.ID_USER_PARAM)));
            request.setAttribute(AttributeName.REDIRECT, "?command=account_command");
            logger.info("Redirect to user offers");
            return "";
        } catch (LogicException ex) {
            logger.error("Logic exception" + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
