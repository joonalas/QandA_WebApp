package database;

import Domain.*;
import Domain.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.List;


public class QuestionDao implements Dao<Question, Integer>{
    private Database database;
    private Dao<Course, Integer> courseDao;
    private Dao<Topic, Integer> topicDao;

    public QuestionDao(Database database, Dao<Course, Integer> courseDao, Dao<Topic, Integer> topicDao) {
        this.database = database;
        this.courseDao = courseDao;
        this.topicDao = topicDao;
    }
    

    @Override
    public Question findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Question WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        //Course course = courseDao.findOne(rs.getInt("course_id"));
        Topic topic = topicDao.findOne(rs.getInt("topic_id"));
        
        Question q = new Question(rs.getInt("id"), /*course,*/ topic, rs.getString("text"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return q;
    }
    
    /*public List<Question> findByCourse(Course course) throws SQLException {
        List<Question> questions = new ArrayList<>();
        try(Connection conn = database.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Question WHERE course_id = ?");
            stmt.setInt(1, course.getId());
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"), 
                        courseDao.findOne(rs.getInt("course_id")), 
                        topicDao.findOne(rs.getInt("topic_id")), rs.getString("text")
                ));
            }
            
            stmt.close();
            rs.close();
            conn.close();
            
            return questions;
        }
    }*/

    @Override
    public List<Question> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Question saveOrUpdate(Question object) throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Question (topic_id, text) VALUES (?, ?)");
            //stmt.setInt(1, object.getCourse().getId());
            stmt.setInt(1, object.getTopic().getId());
            stmt.setString(2, object.getText());
            
            stmt.executeUpdate();
            stmt.close();
            
            conn.close();
            
            return new Question(checkHighestId(), /*object.getCourse(),*/ object.getTopic(), object.getText());
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int checkHighestId() throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM Question");
            
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return 0;
            }
            
            int highestId = rs.getInt("id");
            
            rs.close();
            stmt.close();
            conn.close();
            
            return highestId;
        }
        
    }
    
}
