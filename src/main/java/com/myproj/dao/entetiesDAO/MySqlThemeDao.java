package com.myproj.dao.entetiesDAO;

import com.myproj.dao.AbstractJDBCDao;
import com.myproj.dao.PersistException;
import com.myproj.domain.Theme;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlThemeDao extends AbstractJDBCDao<Theme, Integer> {

    public MySqlThemeDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, theme FROM diplom.theme";
    }

    @Override
    public String getSelectQueryWithWhen() {
        return "SELECT id, theme FROM diplom.theme";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO diplom.theme (theme) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE diplom.theme SET theme= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM diplom.theme WHERE id= ?;";
    }

    @Override
    protected List<Theme> parseResultSet(ResultSet rs) throws PersistException {
        List<Theme> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Theme theme = new Theme();
                theme.setId(rs.getInt("id"));
                theme.setTheme(rs.getString("theme"));
                result.add(theme);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Theme theme) throws PersistException {
        try {
            statement.setObject(1, theme.getTheme());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Theme theme) throws PersistException {
        try {
            statement.setString(1, theme.getTheme());
            statement.setLong(2, theme.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
