package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("EditCommand executed");
            String page = Path.EDIT_PATH;
            User user = UserLogic.INSTANCE.findUser(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
            request.setAttribute(ParamName.LOGIN_PARAM, user.getLogin());
            request.setAttribute(ParamName.EMAIL_PARAM, user.getEmail());
            request.setAttribute(ParamName.FIRSTNAME_PARAM, user.getFirstName());
            request.setAttribute(ParamName.LASTNAME_PARAM, user.getLastName());
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
