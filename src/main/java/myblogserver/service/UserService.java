package myblogserver.service;

import myblogserver.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<User> getUser(String number);

    public Mono<User> getUser(long uid);

    public Mono<Void> resetNickAndSign(String nickName, String sign, long uid);

    public Mono<Void> resetPassword(String password, long uid);

}
