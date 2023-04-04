package myblogserver.repository;

import myblogserver.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByNumber(String number);

    Mono<User> findById(long id);

    @Modifying
    @Query("update user u set u.nick_name=:nickName, u.sign=:sign where u.id=:uid")
    Mono<Integer> updateNickAndSign(String nickName, String sign, long uid);

    @Modifying
    @Query("update user u set u.password=:password where u.id=:uid")
    Mono<Integer> updatePasswordById(String password, long uid);
}
