package com.myproj.utils;

import com.myproj.dao.PersistException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Functional interface with a method that will be redefined to control transactional
 */

@FunctionalInterface
public interface Transaction {
    void execute(Connection connection) throws SQLException, PersistException;
}
