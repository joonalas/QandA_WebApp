package Domain;

import java.sql.Timestamp;


public class Answer {
    private int id;
    private Option option;
    private Timestamp timestamp;

    public Answer(int id, Option option, Timestamp timestamp) {
        this.id = id;
        this.option = option;
        this.timestamp = timestamp;
    }
    
}
