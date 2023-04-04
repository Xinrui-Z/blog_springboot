package myblogserver.service;

import myblogserver.entity.Article;
import reactor.core.publisher.Mono;

public interface ArticleService {

    public Mono<Void> addArticle(Article article);
}
