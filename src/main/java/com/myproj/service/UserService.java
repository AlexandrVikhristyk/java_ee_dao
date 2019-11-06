package com.myproj.service;

import com.myproj.dao.DaoFactory;
import com.myproj.dao.GenericDao;
import com.myproj.dao.MySqlDaoFactory;
import com.myproj.dao.PersistException;
import com.myproj.dao.entetiesDAO.MySqlUserDao;
import com.myproj.domain.User;
import com.myproj.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    private final GenericDao userDao;

    public UserService() throws SQLException, PersistException {
        userDao = MySqlDaoFactory.getInstance().getDao(ConnectionPool.getConnection(), User.class);
    }

}
