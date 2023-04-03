package myblogserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import myblogserver.entity.User;
import myblogserver.repository.UserRepository;
import myblogserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> getUser(String number) {
        return userRepository.findByNumber(number);
    }
}
