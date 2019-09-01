package life.sujunbin.community.community.Provider;

import com.alibaba.fastjson.JSON;
import life.sujunbin.community.community.pojo.AccessToken;
import life.sujunbin.community.community.pojo.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: 苏俊滨
 * @date: 2019/9/1 10:00
 */
@Component
public class GithubProvide {
    /*
    * 返回的code、state作为参数请求获得token
    * */
    public String getAccessToken(AccessToken accessToken) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
             String[] tokenstr = string.split("&");

             String  token = tokenstr[0].split("=")[1];

            return token;
        } catch (IOException e) {


        }
        return null;
    }
    /*
    *
    *使用token访问github获取用户信息
    * */

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }


}
