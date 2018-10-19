package database;

import Domain.Course;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CourseDao implements Dao<Course, Integer> {
    private Database database;

    public CourseDao(Database database) {
        this.database = database;
    }
    

    @Override
    public Course findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        if(!rs.next()) {
            return null;
        }
        
        Course c = new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"));
        
        stmt.close();
        rs.close();
        conn.close();
        
        return c;
    }

    @Override
    public List<Course> findAll() throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course");
            
            ResultSet rs = stmt.executeQuery();
            
            List<Course> courses = new ArrayList<>();
            while(rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name")));
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
            return courses;
        }
    }
    
    public Course findByCode(String code) throws SQLException {
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Course WHERE code = ?");
            stmt.setString(1, code);
            
            ResultSet rs = stmt.executeQuery();
            
            if(!rs.next()) {
                return null;
            }
            
            Course c = new Course(rs.getInt("id"), rs.getString("code"), rs.getString("name"));
            
            stmt.close();
            rs.close();
            conn.close();
            
            return c;
        }
    }

    @Override
    public Course saveOrUpdate(Course object) throws SQLException {
        Course course = findByCode(object.getCode());
        
        if(course != null) {
            return course;
        }
        
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Course (code, name) VALUES (?, ?)");
            stmt.setString(1, object.getCode());
            stmt.setString(2, object.getName());
            
            stmt.executeUpdate();
            stmt.close();
            
            course = findByCode(object.getCode());
            
            conn.close();
            
            return course;
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
