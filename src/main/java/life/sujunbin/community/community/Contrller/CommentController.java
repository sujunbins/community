package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.Mapper.CommentMapper;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.pojo.Comment;
import life.sujunbin.community.community.pojo.CommentDTO;
import life.sujunbin.community.community.pojo.ResultDTO;
import life.sujunbin.community.community.pojo.User;
import life.sujunbin.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 9:59
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");
        if(user==null)
        {
            return ResultDTO.errofof(CustomizeErrorcode.NO_LOGIN );
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentater(user.getId());
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
