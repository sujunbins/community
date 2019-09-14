package life.sujunbin.community.community.Mapper;

import life.sujunbin.community.community.model.Question;
import life.sujunbin.community.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incview( Question record);


    int insertComment(Question question);
}