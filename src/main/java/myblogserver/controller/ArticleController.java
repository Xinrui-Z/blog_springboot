package myblogserver.controller;

import myblogserver.entity.Article;
import myblogserver.entity.Paper;
import myblogserver.service.ArticleService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
//@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加博客
     * @param article
     * @return
     */
    @PostMapping("/api/article/addarticle")
    public Mono<ResultVO> saveArticle(@RequestBody Article article) {
        article.setInsertTime(LocalDateTime.now());
        return articleService.addArticle(article)
                .then(Mono.just(ResultVO.success("添加成功！")));
    }

    /**
     * 修改博客
     * @param article
     * @return
     */
    @PostMapping("/api/article/article")
    public Mono<ResultVO> postArticle(@RequestBody Article article) {
        return articleService.resetArticle(article)
                .then(Mono.just(ResultVO.success("修改成功！")));
    }

    /**
     * 根据id删除博客
     * @param aid
     * @return
     */
    @DeleteMapping("/api/article/deletearticle/{aid}")
    public Mono<ResultVO> deleteArticle(@PathVariable long aid) {
        return articleService.deleteArticle(aid)
                .then(Mono.just(ResultVO.success("删除成功！")));
    }

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);


//    @GetMapping("/api/front/tagsArticle/{label}")
//    public Mono<ResponseEntity<List<Article>>> getArticleByLabel(@PathVariable String label) {
//        log.info("Received data{}",label);
//        Mono<List<Article>> articles = articleService.getArticleByLabel(label);
//        log.info("articles:{}",articles);
//        return articles.map(ResponseEntity::ok);
//    }

    @GetMapping("/api/front/tagsArticle/{label}")
    public Mono<ResponseEntity<List<Article>>> getArticleByLabel(@PathVariable String label) {
        log.info("Received data {}", label);

        Mono<List<Article>> articles = articleService.getArticleByLabel(label);

        return articles.map(articleList -> {
            log.info("articles: {}", articleList);
            return ResponseEntity.ok(articleList);
        });
    }



}