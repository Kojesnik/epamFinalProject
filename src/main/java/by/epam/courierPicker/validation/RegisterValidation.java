package by.epam.courierPicker.validation;

import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ErrorName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.impl.UserDaoImpl;
import by.epam.courierPicker.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class RegisterValidation {

    private static final Logger logger = LogManager.getLogger();

    private static final String LOGIN_REGEX = "^[a-zA-Z][a-zA-Z0-9-_\\.]{5,14}$";
    private static final String NAME_REGEX = "^[a-zA-Z]{2,15}+$";
    private static final String PASSWORD_REGEX = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

    public static Map loginValidation(String login) {
        logger.info("Login validation");
        Map<String, String> req = new HashMap();
        if (login.matches(LOGIN_REGEX)) {
            try {
                if (UserDaoImpl.INSTANCE.findUserByLogin(login) != null) {
                    req.put(AttributeName.ERROR_LOGIN_REGISTER, ErrorName.LOGIN_ALREADY_EXISTS);
                    if (UserDaoImpl.INSTANCE.findUserByLogin(login).getState().equals(ParamName.STATUS_BLOCKED_PARAM)) {
                        req.put(AttributeName.ERROR_LOGIN_REGISTER, ErrorName.USER_BLOCKED);
                    }
                }
            } catch (DaoException ex) {
                logger.error(ex.getMessage());
            }
        } else {
            req.put(AttributeName.ERROR_LOGIN_REGISTER, ErrorName.INCORRECT_LOGIN);
        }
        return req;
    }

    public static Map emailValidation(String email) {
        logger.info("Email validation");
        Map<String, String> req = new HashMap();
        if (email.matches(EMAIL_REGEX)) {
            try {
                if (UserDaoImpl.INSTANCE.findUserByEmail(email) != null) {
                    req.put(AttributeName.ERROR_EMAIL_REGISTER, ErrorName.EMAIL_ALREADY_EXISTS);
                }
            } catch (DaoException ex) {
                logger.error(ex.getMessage());
            }
        } else {
            req.put(AttributeName.ERROR_EMAIL_REGISTER, ErrorName.INCORRECT_EMAIL);
        }
        return req;
    }

    public static Map firstnameValidation(String firstname) {
        logger.info("Firstname validation");
        Map<String, String> req = new HashMap();
        if (!firstname.matches(NAME_REGEX)) {
            req.put(AttributeName.ERROR_FIRSTNAME_REGISTER, ErrorName.INCORRECT_FIRSTNAME);
        }
        return req;
    }

    public static Map lastnameValidation(String lastname) {
        logger.info("Lastname validation");
        Map<String, String> req = new HashMap();
        if (!lastname.matches(NAME_REGEX)) {
            req.put(AttributeName.ERROR_LASTNAME_REGISTER, ErrorName.INCORRECT_LASTNAME);
        }
        return req;
    }

    public static Map passwordValidation(String password) {
        logger.info("Password validation");
        Map<String, String> req = new HashMap();
        if (!password.matches(PASSWORD_REGEX)) {
            req.put(AttributeName.ERROR_PASSWORD_REGISTER, ErrorName.INCORRECT_PASSWORD);
        }
        return req;
    }

}
