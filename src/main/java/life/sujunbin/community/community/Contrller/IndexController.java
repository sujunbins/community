package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.pojo.User;
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

    @RequestMapping("/")
    public String index(HttpServletRequest request)
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

        return "index";
    }
    @RequestMapping("/text")
    public String text()
    {

        return "text";
    }
}
