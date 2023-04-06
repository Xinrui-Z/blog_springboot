package myblogserver.controller;

import com.alibaba.fastjson.JSON;
import myblogserver.entity.Article;
import myblogserver.service.ArticleService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加博客
     * @param article
     * @return
     */
    @PostMapping("/addarticle")
    public Mono<ResultVO> saveArticle(@RequestBody Article article) {
        article.setInsertTime(LocalDateTime.now());
        return articleService.addArticle(article)
                .then(Mono.just(ResultVO.success("添加成功！")));
    }

    /**
     * 获取博客列表
     * @return
     */
    /*
    @GetMapping("/articles/{page}/{pageSize}")
    public Mono<ResultVO> getArticleList(@PathVariable int page, @PathVariable int pageSize) {
        return articleService.listArticles(page, pageSize)
                .map(articles -> ResultVO.success(Map.of("articles", articles)));
    }
    */
    /**
     * 修改博客
     * @param article
     * @return
     */
    @PostMapping("/article")
    public Mono<ResultVO> postArticle(@RequestBody Article article) {
        return articleService.resetArticle(article)
                .then(Mono.just(ResultVO.success("修改成功！")));
    }

    /**
     * 根据id获取博客
     * @param aid
     * @return
     */
    @GetMapping("/article/{aid}")
    public Mono<ResultVO> getArticle(@PathVariable long aid) {
        return  articleService.getArticle(aid)
                .map(article -> ResultVO.success(Map.of("article", article)));
    }

    /**
     * 根据id删除博客
     * @param aid
     * @return
     */
    @DeleteMapping("/deletearticle/{aid}")
    public Mono<ResultVO> deleteArticle(@PathVariable long aid) {
        return articleService.deleteArticle(aid)
                .then(Mono.just(ResultVO.success("删除成功！")));
    }

}
