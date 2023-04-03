package myblogserver.service;

import myblogserver.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {

    public Mono<User> getUserByNumber(String number);
}
