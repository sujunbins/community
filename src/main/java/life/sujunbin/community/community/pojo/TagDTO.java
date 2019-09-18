package life.sujunbin.community.community.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/16 15:46
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String > tags;
}
