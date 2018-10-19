package Domain;


public class Question {
    private int id;
    private Topic topic;
    private String text;

    public Question(int id, Topic topic, String text) {
        this.id = id;
        this.topic = topic;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }
    
    
}
