package myblogserver.controller;

import myblogserver.entity.Article;
import myblogserver.entity.Message;
import myblogserver.entity.Paper;
import myblogserver.service.ArticleService;
import myblogserver.service.MessageService;
import myblogserver.service.PaperService;
import myblogserver.service.UserService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
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

    @Autowired
    private PaperService paperService;

    @Autowired
    private MessageService messageService;


    @GetMapping("/user")
    public Mono<ResultVO> getUser() {
        return userService.getUser()
                .flatMap(user -> Mono.just(ResultVO.success(Map.of("users", user))));
    }

    /**
     * 博客列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/articles/{page}/{pageSize}")
    public Mono<ResultVO> getArticles(@PathVariable int page, @PathVariable int pageSize) {
        Mono<List<Article>> articlesM = articleService.listArticles(page, pageSize);
        Mono<Integer> articlesCountM = articleService.listArticlesCount();
        return articlesCountM.flatMap(total -> articlesM.map(articles ->
                ResultVO.success(Map.of("articles", articles, "total", total)))
        );
    }

    /**
     * 标签列表
     *
     * @return
     */
    @GetMapping("/labels")
    public Mono<ResultVO> getLabelsAndCount() {
        return articleService.listLabelsAndCount()
                .flatMap(labels -> Mono.just(ResultVO.success(Map.of("labels", labels))));
    }

    /**
     * 根据id获取博客信息
     *
     * @param aid
     * @return
     */
    @GetMapping("/article/{aid}")
    public Mono<ResultVO> getArticle(@PathVariable long aid) {
        return articleService.getArticleById(aid)
                .map(article -> ResultVO.success(Map.of("article", article)));
    }

    /**
     * 首页About数据，基于标签获取博客列表
     *
     * @param label
     * @return
     */
    @GetMapping("/about/{label}")
    public Mono<ResultVO> getArticles(@PathVariable String label) {
        return articleService.getArticleByLabel(label)
                .flatMap(articles -> Mono.just(ResultVO.success(Map.of("articles", articles))));
    }

    /**
     * 留言板列表
     *
     * @return
     */
    @GetMapping("/messages")
    public Mono<ResultVO> getMessages() {
        return messageService.listMessages()
                .flatMap(messages -> Mono.just(ResultVO.success(Map.of("messages", messages))));

    }

    /**
     * 保存留言
     *
     * @param message
     * @return
     */
    @PostMapping("/message")
    public Mono<ResultVO> postMessage(@RequestBody Message message) {
        message.setInsertTime(LocalDateTime.now());
        return messageService.addMessage(message)
                .then(Mono.just(ResultVO.success("添加成功")));
    }

    /**
     * 文献列表
     *
     * @param pagep
     * @param pagepSize
     * @return
     */
    @GetMapping("/papers/{pagep}/{pagepSize}")
    public Mono<ResultVO> getPapers(@PathVariable int pagep, @PathVariable int pagepSize) {
        Mono<List<Paper>> papersM = paperService.listPapers(pagep, pagepSize);
        Mono<Integer> papersCountM = paperService.listPapersCount();
        return papersCountM.flatMap(total -> papersM.map(articles ->
                ResultVO.success(Map.of("papers", articles, "total", total)))
        );
    }

    /**
     * 根据id获取文献信息
     *
     * @param aid
     * @return
     */
    @GetMapping("/paper/{aid}")
    public Mono<ResultVO> getPaper(@PathVariable long aid) {
        return paperService.getPaperById(aid)
                .map(paper -> ResultVO.success(Map.of("paper", paper)));
    }

    /**
     * 文献标签列表
     *
     * @return
     */
    @GetMapping("/labelsPaper")
    public Mono<ResultVO> getLabelsPaperAndCount() {
        return paperService.listLabelsAndCount()
                .flatMap(labels -> Mono.just(ResultVO.success(Map.of("labels", labels))));
    }


}
