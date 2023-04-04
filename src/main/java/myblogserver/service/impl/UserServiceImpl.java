package myblogserver.service.impl;

import myblogserver.config.PasswordEncoderConfig;
import myblogserver.entity.User;
import myblogserver.exception.XException;
import myblogserver.repository.UserRepository;
import myblogserver.service.UserService;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> getUser(String number) {
        return userRepository.findByNumber(number);
    }

    @Override
    public Mono<User> getUser(long uid) {
        return userRepository.findById(uid);
    }

    @Override
    public Mono<Void> resetNickAndSign(String nickName, String sign, long uid) {
        return userRepository.updateNickAndSign(nickName, sign, uid)
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }

    @Override
    public Mono<Void> resetPassword(String password, long uid) {
        return userRepository.updatePasswordById(passwordEncoder.encode(password), uid)
                .filter(u -> u != 0)
                .switchIfEmpty(Mono.error(new XException(ResultVO.BAD_REQUEST, "修改失败，请稍后再试")))
                .then();
    }
}
