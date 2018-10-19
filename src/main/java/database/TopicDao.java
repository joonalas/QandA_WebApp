package database;

import java.sql.SQLException;
import java.util.List;
import Domain.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class TopicDao implements Dao<Topic, Integer>{
    private Database database;
    private Dao<Course, Integer> courseDao;

    public TopicDao(Database database, Dao<Course, Integer> courseDao) {
        this.database = database;
        this.courseDao = courseDao;
    }
    

    @Override
    public Topic findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Topic WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        Topic t = new Topic(rs.getInt("id"), rs.getString("name"), courseDao.findOne(rs.getInt("course_id")));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return t;
    }

    @Override
    public List<Topic> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Topic saveOrUpdate(Topic object) throws SQLException {
        Topic topic = findByNameAndCourse(object.getName(), object.getCourse());
        
        if(topic != null) {
            return topic;
        }
        
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Topic (course_id, name) VALUES (?, ?)");
            stmt.setInt(1, object.getCourse().getId());
            stmt.setString(2, object.getName());
            
            stmt.executeUpdate();
            stmt.close();
            
            conn.close();
            
            return new Topic(checkHighestId(), object.getName(), object.getCourse());
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Topic findByNameAndCourse(String name, Course course) throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Topic WHERE name = ? AND course_id = ?");
            stmt.setString(1, name);
            stmt.setInt(2, course.getId());
            
            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                return null;
            }
            
            Topic t = new Topic(rs.getInt("id"), name, course);
            
            stmt.close();
            rs.close();
            conn.close();
            
            return t;
        }
    }
    
    public int checkHighestId() throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id) AS id FROM Topic");
            
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
    
    public List<Topic> findByCourse(Course course) throws SQLException {
        List<Topic> topics = new ArrayList<>();
        try(Connection conn = database.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Topic WHERE course_id = ?");
            stmt.setInt(1, course.getId());
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                topics.add(new Topic(
                        rs.getInt("id"),
                        rs.getString("name"),
                        course)
                );
            }
            
            stmt.close();
            rs.close();
            conn.close();
            
            return topics;
        }
    }
    
}
