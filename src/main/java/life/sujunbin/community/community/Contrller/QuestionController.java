package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.pojo.QuestionDTO;
import life.sujunbin.community.community.service.CommentService;
import life.sujunbin.community.community.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: 苏俊滨
 * @date: 2019/9/7 9:09
 */
@Controller
public class QuestionController {
    @Autowired
    private Questionservice questionservice;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO question = questionservice.getbyid(id);
        //累加阅读数的功能

        questionservice.incview(id);
        model.addAttribute("question", question);
        return "question";
    }

}
