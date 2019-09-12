package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/3 8:57
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    int create(Question question);
    @Select("select *from question limit #{offset},#{size}")
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer count();
    @Select("select *from question where creator = #{userId}  limit #{offset},#{size}")
    List<Question> get_prfile_list(Long userId, Integer offset, Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countbyuser(Long userId);

    @Select("select * from question where id = #{id}")
    Question getbyid(Long id);

    @Update("update question set title = #{title},description = #{description}," +
            "gmt_modified = #{gmtModified},tag = #{tag}, view_count = #{viewCount} ," +
            "comment_count = #{commentCount},like_count = #{likeCount} where id = #{id}")
    void updata(Question question);

    @Update("update question set view_count = view_count+#{viewCount} where id = #{id}")
    void incview(Question question);

    @Update("update question set comment_count = comment_count+#{commentCount} where id = #{id}")
    void insertComment(Long id);
}
