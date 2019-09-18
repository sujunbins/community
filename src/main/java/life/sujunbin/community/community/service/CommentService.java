package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.*;

import life.sujunbin.community.community.enums.CommentTypeEnum;
import life.sujunbin.community.community.enums.NotificationEnum;
import life.sujunbin.community.community.enums.NotificationStatucEnum;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.model.*;
import life.sujunbin.community.community.pojo.CommentDTOS;
import life.sujunbin.community.community.pojo.QuestionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {
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
            Question questionDTO = questionMapper.selectByPrimaryKey(commentdb.getParentId());

            if(questionDTO==null)
            {
                throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);
            }

            commentMapper.insertSelective(comment);
            //增加评论数
            Comment comment1 = new Comment();
            comment1.setParentId(comment.getParentId());
            comment1.setCommentCount(1);
            commentExtMapper.incCommentCount(comment1);


            //创建通知
            createNotify(comment, commentdb.getCommentater(), commentator.getName(), questionDTO.getTitle(), NotificationEnum.REPLY_COMMENT.getType(),questionDTO.getId());
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);

            }

            commentMapper.insertSelective(comment);
            questionservice.insertComment(question.getId());


            //创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_QUESTION.getType(),question.getId());
        }
    }

    private void createNotify(Comment comment, Long commentater, String notifiname, String outerTitle, int type,Long outerid) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentater());
        notification.setStatuc(NotificationStatucEnum.UNREAD.gerStatuc());
        notification.setReceiver(commentater);
        notification.setOuterid(outerid);
        notification.setNotifierName(notifiname);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insertSelective(notification);
    }

    public List<CommentDTOS> listbytypeid(Long id, CommentTypeEnum type) {

        CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream()
                .map(comment -> comment.getCommentater()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转换为Map
        UserExample example1 = new UserExample();
        example1.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(example1);
        Map<Long, User> usermap = users.stream().
                collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment为commentDTOS
        List<CommentDTOS> commentDTOSList = comments.stream().map(comment -> {
                    CommentDTOS commentDTOS = new CommentDTOS();
                    BeanUtils.copyProperties(comment, commentDTOS);
                    commentDTOS.setUser(usermap.get(comment.getCommentater()));
                    return commentDTOS;
                }
        ).collect(Collectors.toList());

        return commentDTOSList;
    }

    public boolean comment_like(Long id) {
        Comment comment1 = new Comment();
        comment1.setId(id);
        comment1.setLikeCount(1l);
        if (commentExtMapper.insLike(comment1) != 0) {
            return true;
        } else {
            return false;
        }
    }

}
