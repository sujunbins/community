package life.sujunbin.community.community.enums;


/**
 * @author: 苏俊滨
 * @date: 2019/9/17 0:35
 */

public enum NotificationEnum {

    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(1, "回复了评论");

    private int type;
    private String name;

    NotificationEnum(int statuc, String name) {
        this.type = statuc;
        this.name = name;
    }

    public static String nameOfType(Integer type) {
        for(NotificationEnum notificationEnum :NotificationEnum.values())
        {
            if(notificationEnum.getType() == type)
            {
                return notificationEnum.getName();
            }
        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }}
