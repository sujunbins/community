package life.sujunbin.community.community.advice;


import com.alibaba.fastjson.JSON;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.pojo.ResultDTO;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static life.sujunbin.community.community.pojo.ResultDTO.errofof;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 8:48
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handler(HttpServletRequest request, Throwable ex, Model model,HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO = null;
            if (ex instanceof CustomizeException) {
                resultDTO = errofof((CustomizeException) ex);

            } else {

                resultDTO = errofof(CustomizeErrorcode.SYSTEM_ERROR );
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            }catch (IOException io)
            {
                ex.printStackTrace();
            }
            return null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorcode.SYSTEM_ERROR.getMessage());

            }
            return new ModelAndView("error");

        }

    }
}



