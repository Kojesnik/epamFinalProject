package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.CourierOfferLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AcceptUserOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            String page = Path.COURIER_PAGE_PATH;
            logger.info("AcceptUserOfferCommand executed");
            CourierOfferLogic.INSTANCE.acceptUserOffer(Integer.parseInt(request.getParameter(ParamName.ID_OFFER_PARAM)), Integer.parseInt(request.getParameter(ParamName.ID_USER_PARAM)));
            request.setAttribute(AttributeName.REDIRECT, "?command=account_command");
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception" + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
