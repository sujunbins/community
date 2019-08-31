package life.sujunbin.community.community.Contrller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HeloController {
    @RequestMapping("/hello")
    public String hrllo(@RequestParam(name = "name")String name, Model model)
    {
        model.addAttribute("name", name);
        return "hello";
    }
}
