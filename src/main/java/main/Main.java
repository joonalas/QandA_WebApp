package main;

import Domain.*;
import database.*;
import database.Database;
import java.util.HashMap;
import spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    public static void main(String[] args) throws Exception {
        Database db = new Database("jdbc:sqlite:test.db");
        
        CourseDao courseDao = new CourseDao(db);
        TopicDao topicDao = new TopicDao(db, courseDao);
        QuestionDao questionDao = new QuestionDao(db, courseDao, topicDao);
        OptionDao optionDao = new OptionDao(db, questionDao);
        
        Spark.get("/", (req, res) -> {
            res.redirect("/index");
            return "/";
        });
        
        Spark.get("/index", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("courses", courseDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/courses", (req, res) -> {
            courseDao.saveOrUpdate(new Course(0, req.queryParams("code"), req.queryParams("name")));
            res.redirect("/index");
            return "/courses";
        });
        
        Spark.get("/courses/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Course course = courseDao.findOne(Integer.parseInt(req.params(":id")));
            map.put("course", course);
            map.put("topics", topicDao.findByCourse(course));
            
            return new ModelAndView(map, "course");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/courses/:id/topics", (req, res) -> {
            topicDao.saveOrUpdate(new Topic(
                    0, 
                    req.queryParams("name"), 
                    courseDao.findOne(Integer.parseInt(req.params(":id")))
            ));
            res.redirect("/courses/" + req.params(":id"));
            return "/courses/:id";
        });
        
        Spark.get("/courses/:cid/topics/:tid", (req, res) -> {
            HashMap map = new HashMap<>();
            Topic topic = topicDao.findOne(Integer.parseInt(req.params(":tid")));
            map.put("topic", topic);
            
            
            return new ModelAndView(map, "topic");
        }, new ThymeleafTemplateEngine());
        
    }
}
