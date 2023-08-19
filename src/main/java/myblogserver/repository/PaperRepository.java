package myblogserver.repository;

import myblogserver.entity.Paper;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Repository
public interface PaperRepository extends ReactiveCrudRepository<Paper, Long> {

    Flux<Paper> findAll();

    Mono<Paper> findById(long aid);

    Flux<Paper> findByLabel(String label);


    Mono<Paper> findFirstByLabel(String label);

    Mono<Integer> deleteById(long aid);

    @Query("select * from paper order by id desc limit :pageSize offset :offset")
    Flux<Paper> findAll(int offset, int pageSize);


    @Query("select count(*) from paper ")
    Mono<Integer> findCount();

    @Modifying
    @Query("update paper a set a.label=:label, a.title=:title, a.author=:author, a.soource=:source, a.content=:content where a.id=:aid")
    Mono<Integer> updatePaperById(String label, String title, String author, String source, String content, long aid);

    @Modifying
    @Query("update paper a set a.label_count=:count where a.label=:label")
    Mono<Integer> updateLabelCountByLabel(String label, int count);

    @Query("select count(*) from paper a where a.label=:label")
    Mono<Integer> findCountByLabel(String label);

    @Query("select distinct label, label_count from paper")
    Flux<Paper> findLabelsAndCount();


}