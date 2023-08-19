package myblogserver.service;

import myblogserver.entity.Article;
import myblogserver.entity.Paper;
import myblogserver.exception.XException;
import myblogserver.repository.ArticleRepository;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Mono<Article> addArticle(Article article) {
        Mono<Article> articleM = articleRepository.findFirstByLabel(article.getLabel());
        return articleRepository.findCountByLabel(article.getLabel())
                .filter(r -> r != 0)
                .flatMap(r -> articleM.flatMap(a -> articleRepository.updateLabelCountByLabel(article.getLabel(), a.getLabelCount() + 1)))
                .flatMap(r -> articleM.flatMap(a -> {
                    article.setLabelCount(a.getLabelCount());
                    return articleRepository.save(article);
                }))
                .switchIfEmpty(articleRepository.save(article));
    }

    public Mono<List<Article>> listArticles(int page, int pageSize) {
        return articleRepository.findAll((page - 1) * pageSize, pageSize).collectList();
    }

    public Mono<Integer> listArticlesCount() {
        return articleRepository.findCount();
    }

    public Mono<Void> resetArticle(Article article) {
        return articleRepository.updateArticleById(article.getLabel(), article.getTitle(), article.getImgUrl(), article.getDigest(), article.getContent(), article.getId())
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }

    public Mono<Article> getArticleById(long aid) {
        return articleRepository.findById(aid);
    }

    public Mono<List<Article>> getArticleByLabel(String label) {
        return articleRepository.findByLabel(label).collectList();
    }

    @Transactional
    public Mono<Void> deleteArticle(long aid) {
        Mono<Article> articleM = articleRepository.findById(aid);
        return articleM.flatMap(article -> articleRepository.updateLabelCountByLabel(
                article.getLabel(), article.getLabelCount() - 1
        )).then(articleRepository.deleteById(aid).then());
    }

    public Mono<List<Article>> listLabelsAndCount() {
        return articleRepository.findLabelsAndCount().collectList();
    }

//    public Mono<List<Article>> getArticleByTag(String label) {
//        // 在这里根据标签从数据库查询相关数据
//        return articleRepository.findByLabel(label).collectList();
//    }
}
