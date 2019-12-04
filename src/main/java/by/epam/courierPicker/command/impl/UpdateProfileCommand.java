package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.CommandException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.logic.UserLogic;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UpdateProfileCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            logger.info("RegisterCommand executed");
            String page = "";
            try {
                Part part = request.getPart("file");
                InputStream inputStream = part.getInputStream();
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map req = UserLogic.INSTANCE.updateUser(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()),
                    request.getParameter(ParamName.LOGIN_PARAM), request.getParameter(ParamName.EMAIL_PARAM),
                    request.getParameter(ParamName.FIRSTNAME_PARAM), request.getParameter(ParamName.LASTNAME_PARAM),
                    request.getParameter(ParamName.PASSWORD_PARAM), request.getParameter(ParamName.PASSWORD2_PARAM));
            if (req.get(ParamName.ID_USER_PARAM) != null) {
                request.setAttribute(AttributeName.REDIRECT, "?command=account_command");
            } else {
                page = Path.EDIT_PATH;
                User user = UserLogic.INSTANCE.findUser(Integer.parseInt(request.getSession().getAttribute(ParamName.ID_USER_PARAM).toString()));
                request.setAttribute(ParamName.LOGIN_PARAM, user.getLogin());
                request.setAttribute(ParamName.EMAIL_PARAM, user.getEmail());
                request.setAttribute(ParamName.FIRSTNAME_PARAM, user.getFirstName());
                request.setAttribute(ParamName.LASTNAME_PARAM, user.getLastName());
                request.setAttribute(AttributeName.ERROR_LOGIN_REGISTER, req.get(AttributeName.ERROR_LOGIN_REGISTER));
                request.setAttribute(AttributeName.ERROR_EMAIL_REGISTER, req.get(AttributeName.ERROR_EMAIL_REGISTER));
                request.setAttribute(AttributeName.ERROR_PASSWORD_REGISTER, req.get(AttributeName.ERROR_PASSWORD_REGISTER));
                request.setAttribute(AttributeName.ERROR_FIRSTNAME_REGISTER, req.get(AttributeName.ERROR_FIRSTNAME_REGISTER));
                request.setAttribute(AttributeName.ERROR_LASTNAME_REGISTER, req.get(AttributeName.ERROR_LASTNAME_REGISTER));
            }
            logger.info("Redirect to " + page);
            return page;
        } catch (LogicException ex) {
            logger.error("Logic exception " + ex.getMessage());
            throw new CommandException(ex.getMessage());
        }
    }
}
