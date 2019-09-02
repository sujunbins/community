package life.sujunbin.community.community.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 苏俊滨
 * @date: 2019/9/2 0:48
 */
@Setter
@Getter
@ToString
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;


}
