package life.sujunbin.community.community.Contrller;


import life.sujunbin.community.community.pojo.Pagintion;
import life.sujunbin.community.community.service.Questionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController {


    @Autowired
    private Questionservice questionservice;
    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model,
    @RequestParam(name = "page",defaultValue = "1")Integer page,
     @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name = "search",required = false)String serach)
    {

        Pagintion pagintion = questionservice.get_index_list(serach,page,size);
        model.addAttribute("pagintion",pagintion);
        model.addAttribute("search", serach);
        return "index";
    }


    @RequestMapping("/ ")
    public String text()
    {

        return "text";
    }
    @RequestMapping("/publish")
    public String publish()
    {

        return "publish";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response)
    {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

}
