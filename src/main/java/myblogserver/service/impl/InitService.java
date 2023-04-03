package myblogserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import myblogserver.entity.User;
import myblogserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 初始化
 */

@Service
@Slf4j
public class InitService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @EventListener(classes = ApplicationReadyEvent.class)
    public Mono<Void> onApplicationEvent() {
        return userRepository.count()
                .filter(r -> r == 0)
                .flatMap(r -> {
                    String number = "20021228";
                    String password = "021228xinrui..";
                    User admin = User.builder()
                            .number(number)
                            .password(encoder.encode(password))
                            .insertTime(LocalDateTime.now())
                            .build();
                    return userRepository.save(admin).then();
                });
    }
}