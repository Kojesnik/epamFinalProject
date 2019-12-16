package by.epam.courierPicker.logic;

import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.ErrorName;
import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.dao.impl.UserDaoImpl;
import by.epam.courierPicker.encoding.Encoding;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.type.RoleType;
import by.epam.courierPicker.validation.RegisterValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public enum UserLogic {

    INSTANCE;

    private static final Logger logger = LogManager.getLogger();

    public Map loginUser(String password, String login) throws LogicException {
        try {
            password = Encoding.encodePassword(password);
            User user = UserDaoImpl.INSTANCE.findUserByLogin(login);
            Map<String, String> req = new HashMap();
            if (user != null && user.getPassword().equals(password) && !RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER).equals(ErrorName.USER_BLOCKED)) {
                req.put(ParamName.ROLE_PARAM, user.getRole().toString().toLowerCase());
                req.put(ParamName.STATUS_PARAM, ParamName.STATUS_AUTORESIZED_PARAM);
                req.put(ParamName.ID_USER_PARAM, Integer.toString(UserDaoImpl.INSTANCE.findUserIdByLogin(user.getLogin())));
            } else {
                req.put(AttributeName.ERROR_LOGIN_PASS, ErrorName.LOGIN_ERROR);
                if (RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER) != null && RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER).equals(ErrorName.USER_BLOCKED)) {
                    req.put(AttributeName.ERROR_BLOCKED_ACCOUNT, ErrorName.USER_BLOCKED);
                }
            }
            return req;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map registerUser(String password, String role, String email, String login, String firstname, String lastname) throws LogicException {
        try {
            Map<String, String> req = new HashMap();
            boolean isValid = true;
            if (RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER) != null) {
                req.put(AttributeName.ERROR_LOGIN_REGISTER, RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER).toString());
                isValid = false;
            }
            if (RegisterValidation.emailValidation(email).get(AttributeName.ERROR_EMAIL_REGISTER) != null) {
                req.put(AttributeName.ERROR_EMAIL_REGISTER, RegisterValidation.emailValidation(email).get(AttributeName.ERROR_EMAIL_REGISTER).toString());
                isValid = false;
            }
            if (RegisterValidation.firstnameValidation(firstname).get(AttributeName.ERROR_FIRSTNAME_REGISTER) != null) {
                req.put(AttributeName.ERROR_FIRSTNAME_REGISTER, RegisterValidation.firstnameValidation(firstname).get(AttributeName.ERROR_FIRSTNAME_REGISTER).toString());
                isValid = false;
            }
            if (RegisterValidation.lastnameValidation(lastname).get(AttributeName.ERROR_LASTNAME_REGISTER) != null) {
                req.put(AttributeName.ERROR_LASTNAME_REGISTER, RegisterValidation.lastnameValidation(lastname).get(AttributeName.ERROR_LASTNAME_REGISTER).toString());
                isValid = false;
            }
            if (RegisterValidation.passwordValidation(password).get(AttributeName.ERROR_PASSWORD_REGISTER) != null) {
                req.put(AttributeName.ERROR_PASSWORD_REGISTER, RegisterValidation.passwordValidation(password).get(AttributeName.ERROR_PASSWORD_REGISTER).toString());
                isValid = false;
            }
            password = Encoding.encodePassword(password);
            User user = User.newBuilder().buildRole(RoleType.valueOf(role.toUpperCase()))
                    .buildEmail(email)
                    .buildLogin(login)
                    .buildPassword(password)
                    .buildFirstName(firstname)
                    .buildLastName(lastname)
                    .build();
            if (isValid) {
                UserDaoImpl.INSTANCE.create(user);
                req.put(ParamName.ROLE_PARAM, user.getRole().toString().toLowerCase());
                req.put(ParamName.STATUS_PARAM, ParamName.STATUS_AUTORESIZED_PARAM);
                req.put(ParamName.ID_USER_PARAM, Integer.toString(UserDaoImpl.INSTANCE.findUserIdByLogin(user.getLogin())));
            }
            return req;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public User findUser(Integer idUser) throws LogicException {
        try {
            User user = UserDaoImpl.INSTANCE.findEntityById(idUser);
            return user;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public Map updateUser(Integer idUser, String login, String email, String firstname, String lastname, String password, String password2) throws LogicException {
        try {
            Map req = new HashMap();
            boolean isValid = true;
            if (!login.equals(ParamName.EMPTY_PARAM) && RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER) != null) {
                req.put(AttributeName.ERROR_LOGIN_REGISTER, RegisterValidation.loginValidation(login).get(AttributeName.ERROR_LOGIN_REGISTER));
                isValid = false;
            } else if (!login.equals(ParamName.EMPTY_PARAM)) {
                UserDaoImpl.INSTANCE.updateLoginById(login, idUser);
            }

            if (!email.equals(ParamName.EMPTY_PARAM) && RegisterValidation.emailValidation(email).get(AttributeName.ERROR_EMAIL_REGISTER) != null) {
                req.put(AttributeName.ERROR_EMAIL_REGISTER, RegisterValidation.emailValidation(email).get(AttributeName.ERROR_EMAIL_REGISTER));
                isValid = false;
            } else if (!email.equals(ParamName.EMPTY_PARAM)) {
                UserDaoImpl.INSTANCE.updateEmailById(email, idUser);
            }

            if (!firstname.equals(ParamName.EMPTY_PARAM) && RegisterValidation.firstnameValidation(firstname).get(AttributeName.ERROR_FIRSTNAME_REGISTER) != null) {
                req.put(AttributeName.ERROR_FIRSTNAME_REGISTER, RegisterValidation.firstnameValidation(firstname).get(AttributeName.ERROR_FIRSTNAME_REGISTER));
                isValid = false;
            } else if (!firstname.equals(ParamName.EMPTY_PARAM)) {
                UserDaoImpl.INSTANCE.updateFirstNameById(firstname, idUser);
            }

            if (!lastname.equals(ParamName.EMPTY_PARAM) && RegisterValidation.lastnameValidation(lastname).get(AttributeName.ERROR_LASTNAME_REGISTER) != null) {
                req.put(AttributeName.ERROR_LASTNAME_REGISTER, RegisterValidation.lastnameValidation(lastname).get(AttributeName.ERROR_LASTNAME_REGISTER));
                isValid = false;
            } else if (!lastname.equals(ParamName.EMPTY_PARAM)) {
                UserDaoImpl.INSTANCE.updateLastNameById(lastname, idUser);
            }
            boolean isEqual = false;
            if (!password.equals(ParamName.EMPTY_PARAM) && password.equals(password2)) {
                isEqual = true;
            } else if (!password.equals(ParamName.EMPTY_PARAM)) {
                req.put(AttributeName.ERROR_PASSWORD_REGISTER, ErrorName.PASSWORDS_NOT_EQUAL);
                isValid = false;
            }
            if (isEqual && RegisterValidation.passwordValidation(password).get(AttributeName.ERROR_PASSWORD_REGISTER) != null) {
                req.put(AttributeName.ERROR_PASSWORD_REGISTER, RegisterValidation.passwordValidation(password).get(AttributeName.ERROR_PASSWORD_REGISTER));
            } else if (!password.equals(ParamName.EMPTY_PARAM)) {
                password = Encoding.encodePassword(password);
                UserDaoImpl.INSTANCE.updatePasswordById(password, idUser);
            }
            if (isValid) {
                req.put(ParamName.ID_USER_PARAM, idUser);
            }
            return req;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean blockUser(Integer idUser) throws LogicException {
        try {
            UserDaoImpl.INSTANCE.updateStateById(idUser, ParamName.STATUS_BLOCKED_PARAM);
            return true;
        } catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

    public boolean unblockUser(Integer idUser) throws LogicException {
        try {
            UserDaoImpl.INSTANCE.updateStateById(idUser, ParamName.STATUS_ACTIVE_PARAM);
            return true;
        }catch (DaoException ex) {
            logger.error("Dao exception " + ex.getMessage());
            throw new LogicException(ex.getMessage());
        }
    }

}
