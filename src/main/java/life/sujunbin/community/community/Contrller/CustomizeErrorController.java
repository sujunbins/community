package life.sujunbin.community.community.Contrller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 9:59
 */
@Controller
@RequestMapping("/error")
public class CustomizeErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error";
    }
    @RequestMapping(
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = this.getStatus(request);
        if(status.is4xxClientError())
        {
            model.addAttribute("message", "你请求出错了，要不要换个试试！！！");
        }
        if(status.is5xxServerError())
        {
            model.addAttribute("message", "服务器冒烟了，要不再试试！！！");
        }
        return  new ModelAndView("error");
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

}
