package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.enums.CommentTypeEnum;
import life.sujunbin.community.community.pojo.CommentDTOS;
import life.sujunbin.community.community.pojo.QuestionDTO;
import life.sujunbin.community.community.service.CommentService;
import life.sujunbin.community.community.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
        List<QuestionDTO> relatedquestion  = questionservice.selectRelated(question);
        List<CommentDTOS> comments = commentService.listbytypeid(id, CommentTypeEnum.QUESTION);

        //累加阅读数的功能
        questionservice.incview(id);
        model.addAttribute("question", question);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedquestion",relatedquestion);
        return "question";
    }

}
