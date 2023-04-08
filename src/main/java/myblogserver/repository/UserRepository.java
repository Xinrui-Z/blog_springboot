package myblogserver.repository;

import myblogserver.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Flux<User> findAll();

    Mono<User> findByNumber(String number);

    Mono<User> findById(long id);

    @Modifying
    @Query("update user u set u.avatar_url = :avatarUrl, u.nick_name=:nickName, u.sign=:sign, u.email=:email, u.github=:github where u.id=:uid")
    Mono<Integer> updateInfo(String avatarUrl,String nickName,String sign,String email, String github, long uid);

    @Modifying
    @Query("update user u set u.password=:password where u.id=:uid")
    Mono<Integer> updatePasswordById(String password, long uid);
}
