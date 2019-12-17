package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.CourierOffer;
import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.entity.Transport;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.CourierOfferLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class FindCourierOffersByParametersCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            String[] transport = request.getParameterValues(ParamName.TRANSPORT_PARAM);
            String[] goods = request.getParameterValues(ParamName.PACKAGE_PARAM);
            Map<User, CourierOffer> offers = CourierOfferLogic.INSTANCE.findApprovedCourierOffers(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            Map<User, CourierOffer> finalOffers = new LinkedHashMap();
            for (Map.Entry<User, CourierOffer> entry: offers.entrySet()) {
                User user = entry.getKey();
                CourierOffer courierOffer = entry.getValue();
                boolean isTransport = true;
                boolean isGoods = true;
                if (transport != null) {
                    isTransport = false;
                    for (Transport currentTransport : courierOffer.getTransport()) {
                        for (String currentTransport2 : transport) {
                            if (currentTransport.getType().equals(currentTransport2)) {
                                isTransport = true;
                            }
                        }
                    }
                }
                if (goods != null) {
                    isGoods = false;
                    for (Goods currentGoods : courierOffer.getGoods()) {
                        for (String currentGoods2 : goods) {
                            if (currentGoods.getType().equals(currentGoods2)) {
                                isGoods = true;
                            }
                        }
                    }
                }
                if (isGoods && isTransport) {
                    finalOffers.put(user, courierOffer);
                }
            }
            request.setAttribute(AttributeName.OFFER_MAP, finalOffers);
            return Path.APPROVED_COURIER_OFFERS_PATH;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
