package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: 苏俊滨
 * @date: 2019/9/2 0:46
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified })")
     int insertuser(User user);
    @Select("select * from user where token = #{token}")
    User findbytoken(String token);
}
