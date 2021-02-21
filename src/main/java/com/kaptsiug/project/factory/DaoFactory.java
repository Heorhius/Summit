package com.kaptsiug.project.factory;

import com.kaptsiug.project.dao.RequestDao;
import com.kaptsiug.project.dao.UserDao;
import com.kaptsiug.project.dao.implementation.RequestDaoImpl;
import com.kaptsiug.project.dao.implementation.UserDaoImpl;

public class DaoFactory {

    private static UserDao userDao = new UserDaoImpl();
    private static RequestDao requestDao = new RequestDaoImpl();

    public static UserDao getUserDao() {
        return userDao;
    }

    public static RequestDao getRequestDao() {
        return requestDao;
    }
}
