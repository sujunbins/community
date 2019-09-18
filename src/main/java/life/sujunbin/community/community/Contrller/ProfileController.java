package life.sujunbin.community.community.Contrller;


import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.pojo.Pagintion;
import life.sujunbin.community.community.service.NotificationService;
import life.sujunbin.community.community.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

/**
 * @author: 苏俊滨
 * @date: 2019/9/6 15:54
 */
@Controller
public class ProfileController {

    @Autowired
    private Questionservice questionservice;

    @Autowired
    private NotificationService notificationServer;

    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            Pagintion pagintion = questionservice.list(user.getId(), page, size);
            model.addAttribute("pagintion", pagintion);
        } else if ("replies".equals(action)) {
            Pagintion pagintion = notificationServer.list(user.getId(), page, size);

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");

            model.addAttribute("pagintion", pagintion);
        }


        return "profile";
    }
}
