package com.myproj.controller;

import com.myproj.dao.DaoFactory;
import com.myproj.dao.GenericDao;
import com.myproj.dao.MySqlDaoFactory;
import com.myproj.dao.PersistException;
import com.myproj.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        method();
        String gg = req.getParameter("gg");
        System.out.println(gg);
        req.getRequestDispatcher("/WEB-INF/templates/test.jsp").forward(req, resp);
    }


    private void method () {
        DaoFactory<Connection> daoFactory = MySqlDaoFactory.getInstance();
        GenericDao dao;
        System.out.println("BEFORE");
        try {
            System.out.println("INNER");
            dao = daoFactory.getDao(daoFactory.getContext(), User.class);
            User user = new User("ggg@gmail.com", "ggggg");
            dao.persist(user);
        } catch (PersistException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER");
    }
}
