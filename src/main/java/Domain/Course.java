package Domain;

import java.util.ArrayList;
import java.util.List;


public class Course {
    private int id;
    //private List<Question> questions;
    private String code;
    private String name;

    public Course(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
        //this.questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    
}
