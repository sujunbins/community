package life.sujunbin.community.community.Contrller;


import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.pojo.Pagintion;
import life.sujunbin.community.community.pojo.User;
import life.sujunbin.community.community.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Questionservice questionservice;
    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model,
    @RequestParam(name = "page",defaultValue = "1")Integer page,
     @RequestParam(name = "size",defaultValue = "5")Integer size)
    {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies)
        {
           if(cookie.getName().equals("token"))
           {
               String token = cookie.getValue();
               User user = userMapper.findbytoken(token);
               if(user!=null)
               {
                  request.getSession().setAttribute("user", user);
               }
               break;
           }
        }
        Pagintion pagintion = questionservice.get_index_list(page,size);
        model.addAttribute("pagintion",pagintion);
        return "index";
    }


    @RequestMapping("/text")
    public String text()
    {

        return "text";
    }
    @RequestMapping("/publish")
    public String publish()
    {

        return "publish";
    }

}
