package life.sujunbin.community.community.Contrller;

import life.sujunbin.community.community.Provider.GithubProvide;
import life.sujunbin.community.community.pojo.AccessToken;
import life.sujunbin.community.community.pojo.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping("/callback")
    public String calback(@RequestParam(name = "code") String code,
                          @RequestParam(name = "state") String state) {
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(client_id);
        accessToken.setClient_secret(client_secret);
        accessToken.setCode(code);
        accessToken.setRedirect_uri(redirect_uri);
        accessToken.setState(state);
        String accesstoken = githubProvide.getAccessToken(accessToken);
        GithubUser githubUser = githubProvide.getUser(accesstoken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
