package database;

import Domain.Answer;
import Domain.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AnswerDao implements Dao<Answer, Integer> {
    private Database database;
    private Dao<Option, Integer> optionDao;

    public AnswerDao(Database database, Dao<Option, Integer> optionDao) {
        this.database = database;
        this.optionDao = optionDao;
    }
    

    @Override
    public Answer findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Answer WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        Option o = optionDao.findOne(rs.getInt("option_id"));
        Answer a = new Answer(rs.getInt("id"), o, rs.getTimestamp("timestamp"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return a;
    }

    @Override
    public List<Answer> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Answer saveOrUpdate(Answer object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
