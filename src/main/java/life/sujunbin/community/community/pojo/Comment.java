package life.sujunbin.community.community.pojo;

import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 9:52
 */
@Data
public class Comment {
    private Integer id;
    private Long parentId;
    private Integer type;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Long commentater;
    private String content;

}
