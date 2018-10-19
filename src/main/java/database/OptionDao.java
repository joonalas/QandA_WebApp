package database;

import Domain.Option;
import Domain.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class OptionDao implements Dao<Option, Integer> {
    private Database database;
    private Dao<Question, Integer> questionDao;

    public OptionDao(Database database, Dao<Question, Integer> questionDao) {
        this.database = database;
        this.questionDao = questionDao;
    }
    

    @Override
    public Option findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Option WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        Question q = questionDao.findOne(rs.getInt("question_id"));
        Option o = new Option(rs.getInt("id"), q, rs.getString("text"), rs.getBoolean("right"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return o;
    }

    @Override
    public List<Option> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Option saveOrUpdate(Option object) throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Option (question_id, text, right) VALUES (?, ?, ?)");
            stmt.setInt(1, object.getQuestion().getId());
            stmt.setString(2, object.getText());
            stmt.setBoolean(3, object.isRight());
            
            stmt.executeUpdate();
            stmt.close();
            
            conn.close();
            
            return object;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
