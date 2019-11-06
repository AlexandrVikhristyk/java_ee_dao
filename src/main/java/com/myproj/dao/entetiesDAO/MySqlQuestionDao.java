package com.myproj.dao.entetiesDAO;

import com.myproj.dao.AbstractJDBCDao;
import com.myproj.dao.PersistException;
import com.myproj.domain.Question;
import com.myproj.domain.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlQuestionDao extends AbstractJDBCDao<Question, Integer> {

    public MySqlQuestionDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return " SELECT diplom.question.id, diplom.question.question, diplom.test.id as test_id, diplom.test.name from diplom.question " +
                "INNER JOIN diplom.test on diplom.question.test_id = diplom.test.id";
    }

    @Override
    public String getSelectQueryWithWhen() {
        return getSelectQuery() + " WHERE diplom.test.name = ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO diplom.question (question) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE diplom.question SET question= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM diplom.question WHERE id= ?;";
    }


    //викликати дао тесту і в тесті вище щоб все підтягувалось рекурсивно !!!!!!!!!!!!!!!!
    @Override
    protected List<Question> parseResultSet(ResultSet rs) throws PersistException {
        List<Question> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Question question = new Question();
                Test test = new Test();
                test.setId(rs.getInt("test_id"));
                test.setName(rs.getString("name"));
                question.setId(rs.getInt("id"));
                question.setQuestion(rs.getString("question"));
                question.setTest(test);
                result.add(question);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Question role) throws PersistException {
        try {
            statement.setString(1, role.getQuestion());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Question role) throws PersistException {
        try {
            statement.setString(1, role.getQuestion());
            statement.setLong(2, role.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected List<Question> getAllByTestName(Test test) throws PersistException {
        return getAll(getSelectQueryWithWhen() + test.getName() + ";");
    }
}
