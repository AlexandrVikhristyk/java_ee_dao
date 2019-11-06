package com.myproj.dao.entetiesDAO;

import com.myproj.dao.AbstractJDBCDao;
import com.myproj.dao.PersistException;
import com.myproj.domain.Role;
import com.myproj.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {

    public MySqlUserDao(Connection connection) {
        super(connection);
    }

        @Override
        public String getSelectQuery() {
            return "SELECT users.email, users.password, roles.id as role_id, roles.role FROM " +
                    "((users INNER JOIN user_role on users.id = user_role.user_id) " +
                    "INNER JOIN roles on user_role.role_id = roles.id);";
        }

    @Override
    public String getSelectQueryWithWhen() {
        return getSelectQuery() + "WHERE roles.role = ";
    }

    @Override
        public String getCreateQuery() {
            return "INSERT INTO diplom.users (email, password) \n" +
                    "VALUES (?, ?); INSERT INTO diplom.user_role (user_id, role_id) VALUES(LAST_INSERT_ID(), ?)";
        }

        @Override
        public String getUpdateQuery() {
            return "UPDATE diplom.users SET email= ?, password = ? WHERE id= ?;";
        }

        @Override
        public String getDeleteQuery() {
            return "DELETE FROM diplom.users WHERE id= ?;";
        }

        @Override
        protected List<User> parseResultSet(ResultSet rs) throws PersistException {
            List<User> result = new LinkedList<>();
            try {
                while (rs.next()) {
                    User user = new User();
                    Role role = new Role();
                    role.setRole(rs.getString("role"));
                    role.setId(rs.getInt("role_id")) ;
                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(role);
                    result.add(user);
                }
            } catch (Exception e) {
                throw new PersistException(e);
            }
            return result;
        }

        @Override
        protected void prepareStatementForInsert(PreparedStatement statement, User user) throws PersistException {
            try {
                statement.setString(1, user.getEmail());
                statement.setString(2, user.getPassword());
                statement.setInt(3, user.getRole().getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
        }

        @Override
        protected void prepareStatementForUpdate(PreparedStatement statement, User user) throws PersistException {
            try {
                statement.setString(1, user.getEmail());
                statement.setLong(2, user.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }
        }

        protected List<User> getAllByRole(Role role) throws PersistException {
            return getAll(getSelectQueryWithWhen() + role.getRole() + ";");
        }
}
