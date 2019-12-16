package by.epam.courierPicker.dao;

import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.DaoException;

public interface UserDao extends BaseDao<Integer, User> {

    User findUserByLogin(String login) throws DaoException;
    Integer findUserIdByLogin(String login) throws DaoException;
    User findUserByEmail(String email) throws DaoException;
    boolean updateLoginById(String login, Integer idUser) throws DaoException;
    boolean updateFirstNameById(String firstname, Integer idUser) throws DaoException;
    boolean updateLastNameById(String lastname, Integer idUser) throws DaoException;
    boolean updateEmailById(String email, Integer idUser) throws DaoException;
    boolean updatePasswordById(String password, Integer idUser) throws DaoException;
    boolean updateStateById(Integer idUser, String state) throws DaoException;

}
