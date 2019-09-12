package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author: 苏俊滨
 * @date: 2019/9/10 9:56
 */
@Mapper
public interface CommentMapper {

    @Insert("insert into comment (parent_id,type,commentater,gmt_modified,gmt_create,content) " +
            "values(#{parentId},#{type},#{commentater},#{gmtModified},#{gmtCreate},#{content})")
    void insert(Comment comment);

    @Select("select *from comment where parent_id = #{parentId}")
    Comment selectbykey(Long parentId);
}
