package life.sujunbin.community.community.pojo;

import life.sujunbin.community.community.model.User;
import lombok.Data;

/**
 * @author: 苏俊滨
 * @date: 2019/9/17 8:54
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer statuc;
    private String notifierName;
    private User notifier;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
