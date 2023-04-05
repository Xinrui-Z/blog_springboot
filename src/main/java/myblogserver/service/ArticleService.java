package myblogserver.service;

import myblogserver.entity.Article;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ArticleService {

    public Mono<Article> addArticle(Article article);

    public Mono<List<Article>> listArticles();

    public Mono<Void> resetArticle(Article article);

    public Mono<Article> getArticle(long aid);
}
