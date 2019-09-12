package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.pojo.User;
import org.apache.ibatis.annotations.*;

/**
 * @author: 苏俊滨
 * @date: 2019/9/2 0:46
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified },#{avatarUrl})")
     int insertuser(User user);
    @Select("select * from user where token = #{token}")
    User findbytoken(String token);
    @Select("select *from user where id = #{id}")
    User  findbyId(@Param("id") Long id);
    @Select("select *from user where account_id = #{accountId}")
    User findbyaccountid(@Param("accountId") String accountId);
    @Update("update user set name = #{name} ,token = #{token}," +
            "gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}" )
    void update(User userdb);
}
