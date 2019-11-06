package com.myproj.dao;

import com.myproj.dao.entetiesDAO.MySqlUserDao;
import com.myproj.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DaoFactory<Connection> {
    private static DaoFactory<Connection> daoFactory;
    private DataSource dataSource;
    private Map<Class, DaoCreator> creators;


    private MySqlDaoFactory() {
        dataSource = new DataSource();
        creators = new HashMap<>();
        try {
            Class.forName(dataSource.getDriver());//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        creators.put(User.class, (connection) -> new MySqlUserDao((Connection) connection));
        creators.put(Role.class, (connection) -> new MySqlUserDao((Connection) connection));
        creators.put(Test.class, (connection) -> new MySqlUserDao((Connection) connection));
        creators.put(Theme.class, (connection) -> new MySqlUserDao((Connection) connection));
        creators.put(Question.class, (connection) -> new MySqlUserDao((Connection) connection));
        creators.put(Answer.class, (connection) -> new MySqlUserDao((Connection) connection));
    }

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUser(), dataSource.getPassword());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }

    public static synchronized DaoFactory<Connection> getInstance() {
        if (daoFactory == null)
            daoFactory = new MySqlDaoFactory();
        return daoFactory;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }
}
