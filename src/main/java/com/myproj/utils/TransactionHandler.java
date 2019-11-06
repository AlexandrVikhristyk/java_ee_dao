package com.myproj.utils;

import com.myproj.dao.PersistException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionHandler {

    /**
     * The purpose of the method is to unite the execution of all called
     * within the framework of a single transaction
     * @param transaction The functional interface in the method of which we will invest
     *                   all the methods that we want to execute in one transaction
     * @see Transaction
     * @throws PersistException if something goes wrong this exception will be thrown
     */
    public static void runInTransaction(Transaction transaction) throws PersistException {
        Connection connection  = null;

        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            transaction.execute(connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                Objects.requireNonNull(connection).rollback();
            } catch (SQLException ex) {
                throw new PersistException(ex);
            }
            throw new PersistException(e);
        } finally {
            try {
                Objects.requireNonNull(connection).setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                throw new PersistException(e);
            }
        }
    }
}
