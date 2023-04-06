package myblogserver.controller;

import myblogserver.service.ArticleService;
import myblogserver.service.UserService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/front")
@CrossOrigin
public class FrontController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/user")
    public Mono<ResultVO> getUser() {
        return userService.getUser()
                .flatMap(user -> Mono.just(ResultVO.success(Map.of("users", user))));
    }

    @GetMapping("/articles/{page}/{pageSize}")
    public Mono<ResultVO> getArticles(@PathVariable int page, @PathVariable int pageSize) {
        return articleService.listArticles(page, pageSize)
                .map(articles -> ResultVO.success(Map.of("articles", articles)));
    }
}
