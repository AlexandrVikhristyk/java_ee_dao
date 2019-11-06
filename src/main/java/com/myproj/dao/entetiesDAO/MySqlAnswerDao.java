package com.myproj.dao.entetiesDAO;

import com.myproj.dao.AbstractJDBCDao;
import com.myproj.dao.PersistException;
import com.myproj.domain.Answer;
import com.myproj.domain.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlAnswerDao extends AbstractJDBCDao<Answer, Integer> {


    public MySqlAnswerDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT answer.id, answer.answer, question.id as question_id, question.question FROM answer " +
                "INNER JOIN question ON answer.question_id = question.id;";
    }

    @Override
    public String getSelectQueryWithWhen() {
        return getSelectQuery() + "WHERE question.question = ";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO diplom.answer (question) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE diplom.answer SET answer= ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM diplom.answer WHERE id= ?;";
    }

    @Override
    protected List<Answer> parseResultSet(ResultSet rs) throws PersistException {
        List<Answer> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Answer answer = new Answer();
                Question question = new Question();
                question.setId(rs.getInt("question_id"));
                question.setQuestion(rs.getString("question"));
                answer.setId(rs.getInt("id"));
                answer.setAnswer(rs.getString("answer"));
                answer.setQuestion(question);
                result.add(answer);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Answer answer) throws PersistException {
        try {
            statement.setString(1, answer.getAnswer());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Answer answer) throws PersistException {
        try {
            statement.setString(1, answer.getAnswer());
            statement.setLong(2, answer.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    protected List<Answer> getAllByQuestion(Question question) throws PersistException {
        return getAll(getSelectQueryWithWhen() + question.getQuestion() + ";");
    }
}
