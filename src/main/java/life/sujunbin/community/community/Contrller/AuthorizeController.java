package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.Mapper.UserMapper;
import life.sujunbin.community.community.Provider.GithubProvide;
import life.sujunbin.community.community.pojo.AccessToken;
import life.sujunbin.community.community.pojo.GithubUser;
import life.sujunbin.community.community.pojo.User;
import life.sujunbin.community.community.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvide githubProvide;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @Autowired
    private Userservice userservice;

    @RequestMapping("/callback")
    public String calback(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state,
                          HttpServletResponse response) {
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(client_id);
        accessToken.setClient_secret(client_secret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirect_uri);
        accessToken.setState(state);
        String accesstoken = githubProvide.getAccessToken(accessToken);
        GithubUser githubUser = githubProvide.getUser(accesstoken);

        if(githubUser.getName()!=null&&githubUser.getId() !=null)
        {
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatarUrl());
            userservice.creat_or_update(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        }else{

            return "redirect:/";
        }

    }
}
