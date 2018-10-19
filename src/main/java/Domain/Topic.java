package Domain;


public class Topic {
    private int id;
    private String name;
    private Course course;

    public Topic(int id, String name, Course course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }
    
    
}
