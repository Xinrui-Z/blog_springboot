package myblogserver.service;

import myblogserver.entity.User;
import myblogserver.exception.XException;
import myblogserver.repository.UserRepository;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<List<User>> getUser() {
        return userRepository.findAll().collectList();
    }

    public Mono<User> getUser(String number) {
        return userRepository.findByNumber(number);
    }

    public Mono<User> getUser(long uid) {
        return userRepository.findById(uid);
    }

    public Mono<Void> resetInfo(User user, long uid) {
        return userRepository.updateInfo(user.getAvatarUrl(),user.getNickName(), user.getSign(),user.getEmail(), user.getGithub(), uid)
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }

    public Mono<Void> resetPassword(String password, long uid) {
        return userRepository.updatePasswordById(passwordEncoder.encode(password), uid)
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }
}
