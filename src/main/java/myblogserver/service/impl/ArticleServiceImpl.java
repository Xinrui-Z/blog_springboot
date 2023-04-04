package myblogserver.service.impl;

import myblogserver.entity.Article;
import myblogserver.repository.ArticleRepository;
import myblogserver.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Mono<Void> addArticle(Article article) {
       return articleRepository.save(article).then();
    }
}
