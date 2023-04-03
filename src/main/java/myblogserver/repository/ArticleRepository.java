package myblogserver.repository;

import myblogserver.entity.Article;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface ArticleRepository extends ReactiveCrudRepository<Article, Long> {
    
}
