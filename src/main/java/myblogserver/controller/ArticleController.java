package myblogserver.controller;

import myblogserver.entity.Article;
import myblogserver.service.ArticleService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/articles")
    public Mono<ResultVO> saveArticle(@RequestBody Article article) {
        article.setInsertTime(LocalDateTime.now());
        return articleService.addArticle(article)
                .then(Mono.just(ResultVO.success("添加成功！")));
    }
}
