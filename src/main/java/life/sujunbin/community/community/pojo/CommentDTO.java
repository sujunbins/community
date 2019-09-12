package life.sujunbin.community.community.pojo;

import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 10:02
 */
@Data
public class CommentDTO {
    private Long parentId;
    private Integer type;
    private String content;

}
