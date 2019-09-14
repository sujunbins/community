package life.sujunbin.community.community.service;

import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.model.UserExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/8 23:17
 */
@Service
public class Userservice {
    @Autowired
    private UserMapper usermapper;


    public void creat_or_update(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());

        List<User> users = usermapper.selectByExample(userExample);
        if(users.size()==0)
        {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usermapper.insert(user);
        }else{
            //跟新
            User update = users.get(0);
            User userdb = new User();
            userdb.setGmtModified(System.currentTimeMillis());
            userdb.setAvatarUrl(user.getAvatarUrl());
            userdb.setName(user.getName());
            userdb.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(update.getId());
            usermapper.updateByExampleSelective(userdb,example);
        }
    }
}
