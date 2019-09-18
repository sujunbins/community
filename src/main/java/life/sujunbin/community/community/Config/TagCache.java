package life.sujunbin.community.community.Config;

import life.sujunbin.community.community.pojo.TagDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 苏俊滨
 * @date: 2019/9/16 15:47
 */
@Data
    public class TagCache {
    public static List<TagDTO>get()
    {
        List<TagDTO> tagDTO = new ArrayList<>();
        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","java","css","html","node","python","node"));
        tagDTO.add(program);

        TagDTO framwork = new TagDTO();
        framwork.setCategoryName("平台框架");
        framwork.setTags(Arrays.asList("laravel","spring","express","django","flask","yii","ru-on-rails","torando","koa","struts"));
        tagDTO.add(framwork);

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat","负载均衡","unix"));
        tagDTO.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","sql","oracle","nosql memcached","sqlserver","postgreswl","sqlite"));
        tagDTO.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode intellij-idea","eclipse","maven","svn","vc++"));
        tagDTO.add(tool);


        return tagDTO;
    }
    public static String fillterInvalid(String tags)
    {
        String[] sqlit = StringUtils.split(tags, "，");
        List<TagDTO> tagDTOS = get();
        List<String> taglist = tagDTOS.stream().flatMap(tag->tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(sqlit).filter(t->!taglist.contains(t)).collect(Collectors.joining());
        return invalid;
    }


}
