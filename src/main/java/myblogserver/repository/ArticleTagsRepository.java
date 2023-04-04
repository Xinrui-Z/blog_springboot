package myblogserver.repository;

import myblogserver.entity.ArticleTags;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagsRepository extends ReactiveCrudRepository<ArticleTags, Long> {
}
