package myblogserver.controller;

import myblogserver.entity.Article;
import myblogserver.service.ArticleService;
import myblogserver.service.UserService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
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
        Mono<List<Article>> articlesM = articleService.listArticles(page, pageSize);
        Mono<Integer> articlesCountM = articleService.getArticlesCount();
        return articlesCountM.flatMap(total -> articlesM.map(articles ->
                ResultVO.success(Map.of("articles",articles, "total",total)))
        );
    }

    @GetMapping("/labels")
    public Mono<ResultVO> getLabelsAndCount() {
        return articleService.getLabelsAndCount()
                .flatMap(articles -> Mono.just(ResultVO.success(Map.of("articles",articles))));
    }
}
