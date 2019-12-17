package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.*;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.CourierOfferLogic;
import by.epam.courierPicker.logic.UserOfferLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class FindUserOffersByParametersCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            String[] goods = request.getParameterValues(ParamName.PACKAGE_PARAM);
            Map<UserOffer, User> offers = UserOfferLogic.INSTANCE.findApprovedUserOffers(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            Map<UserOffer, User> finalOffers = new LinkedHashMap();
            for (Map.Entry<UserOffer, User> entry: offers.entrySet()) {
                User user = entry.getValue();
                UserOffer userOffer = entry.getKey();
                boolean isGoods = true;
                if (goods != null) {
                    isGoods = false;
                    for (Goods currentGoods : userOffer.getGoods()) {
                        for (String currentGoods2 : goods) {
                            if (currentGoods.getType().equals(currentGoods2)) {
                                isGoods = true;
                            }
                        }
                    }
                }
                if (isGoods) {
                    finalOffers.put(userOffer, user);
                }
            }
            request.setAttribute(AttributeName.OFFER_MAP, finalOffers);
            return Path.APPROVED_USER_OFFERS_PATH;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
