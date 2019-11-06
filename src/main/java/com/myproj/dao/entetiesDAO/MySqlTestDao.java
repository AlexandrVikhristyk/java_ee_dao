package com.myproj.dao.entetiesDAO;

import com.myproj.dao.AbstractJDBCDao;
import com.myproj.dao.PersistException;
import com.myproj.domain.Role;
import com.myproj.domain.Test;
import com.myproj.domain.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlTestDao extends AbstractJDBCDao<Test, Integer> {

    public MySqlTestDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT test.id, test.name, theme.id as theme_id, theme.theme FROM diplom.test INNER JOIN diplom.theme ON diplom.test.theme_id = diplom.theme.id;";
    }

    @Override
    public String getSelectQueryWithWhen() {
        return "SELECT test.id, test.name, theme.id as theme_id, theme.theme FROM diplom.test INNER JOIN diplom.theme ON diplom.test.theme_id = diplom.theme.id where theme.theme = ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO diplom.test (name, theme_id) \n" +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE diplom.test SET name= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM diplom.test WHERE id= ?;";
    }

    @Override
    protected List<Test> parseResultSet(ResultSet rs) throws PersistException {
        List<Test> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Test test = new Test();
                Theme theme = new Theme();
                theme.setId(rs.getInt("theme_id"));
                theme.setTheme(rs.getString("theme"));
                test.setId(rs.getInt("id"));
                test.setName(rs.getString("name"));
                test.setTheme(theme);
                result.add(test);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Test test) throws PersistException {
        try {
            statement.setString(1, test.getName());
            statement.setObject(2, test.getTheme());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Test test) throws PersistException {
        try {
            statement.setString(1, test.getName());
            statement.setLong(2, test.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected List<Test> getAllByTheme(Theme theme) throws PersistException {
        return getAll(getSelectQueryWithWhen() + theme.getTheme() + ";");
    }
}
