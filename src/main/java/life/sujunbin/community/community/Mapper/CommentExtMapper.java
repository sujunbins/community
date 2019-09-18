package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.model.Comment;

/**
 * @author: 苏俊滨
 * @date: 2019/9/15 15:23
 */
public interface CommentExtMapper {

    int incCommentCount(Comment comment);

    int insLike(Comment  comment);
}
