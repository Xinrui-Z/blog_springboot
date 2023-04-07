package myblogserver.repository;

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

    Mono<Article> findById(long aid);

    Mono<Article> findFirstByLabel(String label);

    Mono<Integer> deleteById(long aid);

    @Query("select * from article order by update_time desc limit :page,:pageSize")
    Flux<Article> findAll(int page, int pageSize);

    @Query("select count(*) from article ")
    Mono<Integer> findCount();

    @Modifying
    @Query("update article a set a.label=:label, a.title=:title, a.img_url=:imgUrl, " +
            "a.digest=:digest, a.content=:content where a.id=:aid")
    Mono<Integer> updateArticleById(String label, String title, String imgUrl, String digest, String content, long aid);

    @Modifying
    @Query("update article a set a.label_count=:count where a.label=:label")
    Mono<Integer> updateLabelCountByLabel(String label, int count);

    @Query("select count(*) from article a where a.label=:label")
    Mono<Integer> findCountByLabel(String label);

    @Query("select distinct label, label_count from article")
    Flux<Article> findLabelsAndCount();

}
