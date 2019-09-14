package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.CommentMapper;

import life.sujunbin.community.community.enums.CommentTypeEnum;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.model.Comment;
import life.sujunbin.community.community.pojo.QuestionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            Comment commentdb = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (commentdb == null) {
                throw new CustomizeException(CustomizeErrorcode.COMMENT_NOT_FOUND);

            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            QuestionDTO question = questionservice.getbyid(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);

            }

            commentMapper.insertSelective(comment);
            questionservice.insertComment(question.getId());
        }
    }

    public List<Comment> listbyquestionid(Long id)
    {

        return null;
    }
}
