package Domain;


public class Question {
    private int id;
    //private Course course;
    private Topic topic;
    private String text;

    public Question(int id, /*Course course,*/ Topic topic, String text) {
        this.id = id;
        //this.course = course;
        this.topic = topic;
        this.text = text;
        //this.options = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    /*public Course getCourse() {
        return course;
    }*/

    public Topic getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }
    
    
}
