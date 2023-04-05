package myblogserver.service.impl;

import myblogserver.entity.Article;
import myblogserver.exception.XException;
import myblogserver.repository.ArticleRepository;
import myblogserver.service.ArticleService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    @Transactional
    public Mono<Article> addArticle(Article article) {
        Mono<Article> articleM = articleRepository.findFirstByLabel(article.getLabel());
       return articleRepository.findCountByLabel(article.getLabel())
               .filter(r -> r != 0)
               .flatMap(r -> articleM.flatMap(a -> articleRepository.updateLabelCountByLabel(article.getLabel(), a.getLabelCount()+1)))
               .flatMap(r -> articleM.flatMap(a -> {
                   article.setLabelCount(a.getLabelCount());
                   return articleRepository.save(article);
               }))
               .switchIfEmpty(articleRepository.save(article));
    }

    @Override
    @Transactional
    public Mono<List<Article>> listArticles() {
        return  articleRepository.findAll().collectList();
    }

    @Override
    @Transactional
    public Mono<Void> resetArticle(Article article) {
        return articleRepository.updateArticleById(article.getLabel(), article.getTitle(), article.getImgUrl(), article.getDigest(), article.getContent(), article.getId())
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }

    @Override
    @Transactional
    public Mono<Article> getArticle(long aid) {
        return articleRepository.findById(aid);
    }
}
