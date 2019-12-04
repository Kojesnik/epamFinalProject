package by.epam.courierPicker.dao;

import by.epam.courierPicker.entity.Transport;
import by.epam.courierPicker.exception.DaoException;

public interface TransportDao extends BaseDao<Integer, Transport> {

    Integer findIdByType(String type) throws DaoException;

}
