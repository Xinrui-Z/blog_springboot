package myblogserver.repository;

import myblogserver.entity.Article;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ArticleRepository extends ReactiveCrudRepository<Article, Long> {
    
}
