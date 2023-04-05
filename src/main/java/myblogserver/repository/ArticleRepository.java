package myblogserver.repository;

import lombok.extern.slf4j.Slf4j;
import myblogserver.entity.Article;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ArticleRepository extends ReactiveCrudRepository<Article, Long> {

    Flux<Article> findAll();

    @Modifying
    @Query("update article a set a.label=:label, a.title=:title, a.img_url=:imgUrl, " +
            "a.digest=:digest, a.content=:content where a.id=:aid")
    Mono<Integer> updateArticleById(String label, String title, String imgUrl, String digest, String content, long aid);

    Mono<Article> findById(long aid);

    Mono<Article> findFirstByLabel(String label);

    @Modifying
    @Query("update article a set a.label_count=:count where a.label=:label")
    Mono<Integer> updateLabelCountByLabel(String label, int count);

    @Modifying
    @Query("select count(*) from article a where a.label=:label")
    Mono<Integer> findCountByLabel(String label);
}
