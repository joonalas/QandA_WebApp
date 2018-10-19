package Domain;

import java.util.ArrayList;
import java.util.List;


public class Option {
    private int id;
    private Question question;
    //private List<Answer> answers;
    private String text;
    private boolean right;

    public Option(int id, Question question, String text, boolean right) {
        this.id = id;
        this.question = question;
        this.text = text;
        this.right = right;
        //this.answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getText() {
        return text;
    }

    public boolean isRight() {
        return right;
    }
    
    
}
