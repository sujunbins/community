package life.sujunbin.community.community.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 苏俊滨
 * @date: 2019/9/3 8:59
 */
@Setter
@Getter
@ToString
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    public Question(String title,String description,
                    int creator,String tag )
    {
        this.title = title;
        this.creator = creator;
        this.description = description;
        this.tag = tag;

    }
    public Question()
    {

    }
}
