package life.qingfeng.community.controller;

import life.qingfeng.community.dto.AccessTokenDTO;
import life.qingfeng.community.dto.GithubUser;
import life.qingfeng.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("&{github.client.id}")
    private String clientId;
    @Value("&{github.client.secret}")
    private String clientSecret;
    @Value("&{github.redirect.uri}")
    private String clientUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        System.out.println(code);
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
     //   System.out.println(accessToken);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println("user======"+user.getName());
        return "index";
    }
}
