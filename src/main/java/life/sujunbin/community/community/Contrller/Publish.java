package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.Config.TagCache;
import life.sujunbin.community.community.model.Question;
import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.pojo.QuestionDTO;

import life.sujunbin.community.community.pojo.TagDTO;
import life.sujunbin.community.community.service.Questionservice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/3 9:42
 */
@Controller
public class Publish {

    @Autowired
    private Questionservice questionservice;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionservice.getbyid(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());

        return "publish";
    }


    @GetMapping("/publish")
    public String publish(Model model)
    {
        List<TagDTO> tags = TagCache.get();
        model.addAttribute("tags", tags);
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request, Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "补充内容不能为空");
            System.out.println("补充内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        String invalid = TagCache.fillterInvalid(tag);
        if(StringUtils.isNoneBlank(invalid))
        {
            model.addAttribute("error", "输入非法标签"+invalid);
            return "publish";

        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTag(tag);
        question.setDescription(description);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setId(id);
        questionservice.create_or_update(question);
        return "redirect:/";
    }

}
