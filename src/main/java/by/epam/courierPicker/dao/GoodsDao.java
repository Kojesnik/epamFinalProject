package by.epam.courierPicker.dao;

import by.epam.courierPicker.entity.Goods;
import by.epam.courierPicker.exception.DaoException;

public interface GoodsDao extends BaseDao<Integer, Goods> {

    Integer findIdByType(String type) throws DaoException;

}
