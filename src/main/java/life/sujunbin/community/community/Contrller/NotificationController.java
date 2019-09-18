package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.enums.NotificationEnum;
import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.pojo.NotificationDTO;
import life.sujunbin.community.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 苏俊滨
 * @date: 2019/9/17 16:44
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationServer;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id")Long id)
    {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null)
        {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationServer.read(id,user);
        if(NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
        ||NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType())
        {
            return "redirect:/question/"+notificationDTO.getOuterid();

        }else {
            return "redirect:/";
        }

    }
}
