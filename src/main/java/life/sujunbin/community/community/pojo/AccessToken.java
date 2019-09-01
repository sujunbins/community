package life.sujunbin.community.community.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: 苏俊滨
 * @date: 2019/9/1 10:02
 */
@Setter
@Getter
@ToString
public class AccessToken {
    private  String code;
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String state;
}
