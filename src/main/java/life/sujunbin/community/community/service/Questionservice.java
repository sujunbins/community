package life.sujunbin.community.community.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import life.sujunbin.community.community.Mapper.QuestionMapper;
import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.pojo.Pagintion;
import life.sujunbin.community.community.pojo.Question;
import life.sujunbin.community.community.pojo.QuestionDTO;
import life.sujunbin.community.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 苏俊滨
 * @date: 2019/9/5 8:12
 */
@Service
public class Questionservice {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;


    public Pagintion get_index_list(Integer page, Integer size) {
        Pagintion pagintion = new Pagintion();
        Integer totalCount = questionMapper.count();
        pagintion.setPagintion(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > pagintion.getTotalpage()) {
            page = pagintion.getTotalpage();
        }
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findbyId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pagintion.setQuestionDTOS(questionDTOS);


        return pagintion;
    }
}
