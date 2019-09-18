package life.sujunbin.community.community.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 苏俊滨
 * @date: 2019/9/7 8:32
 */
@Configuration
public class Webconfig implements WebMvcConfigurer {
    @Autowired
    private Sessioninterceptor sessioninterceptor;
    @Value("${File.upload}")
    private String path;
    public void  addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(sessioninterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/uploaded_files/**").addResourceLocations("file:"+path);

    }

}
