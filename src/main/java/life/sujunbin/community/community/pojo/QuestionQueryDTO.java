package life.sujunbin.community.community.pojo;

import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/18 11:05
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private int page;
    private int size;
}
