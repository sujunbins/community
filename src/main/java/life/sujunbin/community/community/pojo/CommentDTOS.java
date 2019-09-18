package life.sujunbin.community.community.pojo;

import life.sujunbin.community.community.model.User;
import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/14 16:24
 */
@Data
public class CommentDTOS {

    private Long id;


    private Long parentId;


    private Integer type;

    private Long commentater;


    private Long gmtCreate;

    private Long gmtModified;


    private Long likeCount;

    private String content;
    private User user;
    private Integer commentCount;
}
