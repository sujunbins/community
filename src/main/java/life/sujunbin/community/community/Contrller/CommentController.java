package life.sujunbin.community.community.Contrller;


import life.sujunbin.community.community.enums.CommentTypeEnum;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.model.Comment;
import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.pojo.CommentDTO;
import life.sujunbin.community.community.pojo.CommentDTOS;
import life.sujunbin.community.community.pojo.ResultDTO;
import life.sujunbin.community.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
        if(commentDTO==null|| StringUtils.isBlank(commentDTO.getContent()))
        {
            return ResultDTO.errofof(CustomizeErrorcode.COMMENT_IS_EMPTY );
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentater(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.okof();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}")
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id)
    {

        List<CommentDTOS> listbyquestionid = commentService.listbytypeid(id, CommentTypeEnum.COMMENT);

        return ResultDTO.okof(listbyquestionid);
    }
    @ResponseBody
    @RequestMapping(value = "/comment/like")
    public ResultDTO comments_like(@RequestParam(name = "id")String id)
    {
        if(commentService.comment_like(Long.valueOf(id))) {
            return new ResultDTO().okof();
        }
        else {
            return new ResultDTO().errofof(CustomizeErrorcode.SYSTEM_ERROR);
        }
    }


}
