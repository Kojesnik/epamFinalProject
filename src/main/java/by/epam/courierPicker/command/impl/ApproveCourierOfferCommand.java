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

public class ApproveCourierOfferCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("ApproveCourierOfferCommand executed");
            String page = Path.ADMIN_PAGE_PATH;
            CourierOfferLogic.INSTANCE.approveCourierOffer(Integer.parseInt(request.getParameter(ParamName.ID_OFFER_PARAM)));
            request.setAttribute(AttributeName.REDIRECT, "?command=admin_button&button=courier_offers");
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
