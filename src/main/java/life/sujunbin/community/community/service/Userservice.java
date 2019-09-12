package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 23:17
 */
@Service
public class Userservice {
    @Autowired
    private UserMapper usermapper;


    public void creat_or_update(User user) {
        User userdb = usermapper.findbyaccountid(user.getAccountId());
        if(userdb==null)
        {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usermapper.insertuser(user);
        }else{
            //跟新
            userdb.setGmtModified(System.currentTimeMillis());
            userdb.setAvatarUrl(user.getAvatarUrl());
            userdb.setName(user.getName());
            userdb.setToken(user.getToken());
            usermapper.update(userdb);
        }
    }
}
