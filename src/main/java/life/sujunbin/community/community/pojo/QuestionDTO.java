package life.sujunbin.community.community.pojo;

import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/4 16:12
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private User user;
}
