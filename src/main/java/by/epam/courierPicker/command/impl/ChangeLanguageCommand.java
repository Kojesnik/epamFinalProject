package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguageCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) {
        logger.info("ChangeLanguageCommand executed");
        String page = "";
        if (request.getParameter(AttributeName.REDIRECT) == null) {
            page = "/jsp/" + request.getParameter(ParamName.JSP_PARAM);
        } else {
            request.setAttribute(AttributeName.REDIRECT, "?command=" + request.getParameter(AttributeName.REDIRECT));
        }
        request.getSession().setAttribute(ParamName.LANG_PARAM, request.getParameter(ParamName.LANG_PARAM));
        logger.info("Redirect to " + page);
        return page;
    }
}
