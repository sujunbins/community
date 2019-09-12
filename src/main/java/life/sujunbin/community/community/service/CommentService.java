package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.CommentMapper;
import life.sujunbin.community.community.Mapper.QuestionMapper;
import life.sujunbin.community.community.enums.CommentTypeEnum;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.pojo.Comment;
import life.sujunbin.community.community.pojo.Question;
import life.sujunbin.community.community.pojo.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 21:34
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private Questionservice questionservice;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorcode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorcode.TYPE_NOT_FOUND);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment commentdb = commentMapper.selectbykey(comment.getParentId());
            if (commentdb == null) {
                throw new CustomizeException(CustomizeErrorcode.COMMENT_NOT_FOUND);

            }
            commentMapper.insert(comment);
        } else {
           QuestionDTO question = questionservice.getbyid(comment.getParentId());
           if(question==null)
           {
               throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);

           }
            commentMapper.insert(comment);
            questionservice.insertComment(question.getId());

            //回复问题

        }
    }
}
