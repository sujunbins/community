package life.sujunbin.community.community.service;


import life.sujunbin.community.community.Mapper.QuestionExtMapper;
import life.sujunbin.community.community.Mapper.QuestionMapper;
import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.exception.CustomizeErrorcode;
import life.sujunbin.community.community.exception.CustomizeException;
import life.sujunbin.community.community.model.QuestionExample;
import life.sujunbin.community.community.model.User;
import life.sujunbin.community.community.model.UserExample;
import life.sujunbin.community.community.pojo.Pagintion;
import life.sujunbin.community.community.model.Question;
import life.sujunbin.community.community.pojo.QuestionDTO;
import life.sujunbin.community.community.pojo.QuestionQueryDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public Pagintion get_index_list(String search,Integer page, Integer size) {

        if(StringUtils.isNoneBlank(search))
        {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        Pagintion<QuestionDTO> pagintion = new Pagintion<>();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = (int)questionExtMapper.countBySearch(questionQueryDTO);

        pagintion.setPagintion(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > pagintion.getTotalpage()) {
            page = pagintion.getTotalpage();
        }

        Integer offset = size * (page - 1);

        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);

        List<QuestionDTO> questionDTOS = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        pagintion.setData(questionDTOS);


        return pagintion;
    }

    public Pagintion list(Long userId, Integer page, Integer size) {
        Pagintion<QuestionDTO> pagintion = new Pagintion<>();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
        pagintion.setPagintion(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > pagintion.getTotalpage()) {
            page = pagintion.getTotalpage();
        }
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pagintion.setData(questionDTOS);


        return pagintion;
    }

    public QuestionDTO getbyid(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null)
        {
            throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void create_or_update(Question question) {

        if(question.getId()==null)
        {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else {
           //跟新
            try {
                Question updatequestion = new Question();
                updatequestion.setGmtModified(System.currentTimeMillis());
                updatequestion.setTitle(question.getTitle());
                updatequestion.setDescription(question.getDescription());
                updatequestion.setTag(question.getTag());
                QuestionExample questionExample = new QuestionExample();
                questionExample.createCriteria()
                        .andIdEqualTo(question.getId());
                questionMapper.updateByExampleSelective(updatequestion,questionExample);
            }catch (Exception e)
            {
                throw new CustomizeException(CustomizeErrorcode.QUESTION_NOT_FOUND);
            }

        }
    }

    public void incview(Long id) {
       Question question = new Question();
       question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incview(question);
    }

    public void insertComment(Long id) {
        Question question =new Question();
        question.setId(id);
        question.setCommentCount(1);
        questionExtMapper.insertComment(question);

    }
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO)
    {
        if(StringUtils.isBlank(questionDTO.getTag()))
        {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(),"，");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q->{
            QuestionDTO questiondto = new QuestionDTO();
            BeanUtils.copyProperties(q, questiondto);
            return questiondto;
        }).collect(Collectors.toList());
        return questionDTOS;
    }



}

