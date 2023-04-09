package myblogserver.controller;

import myblogserver.entity.User;
import myblogserver.service.UserService;
import myblogserver.utils.JwtUtil;
import myblogserver.utils.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Mono<ResultVO> login (@RequestBody User user, ServerHttpResponse response) {
        return userService.getUser(user.getNumber())
                .filter(u -> passwordEncoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> {
                    String jwt = JwtUtil.createJWT(Map.of("uid", u.getId()));
                    response.getHeaders().add("token", jwt);

                    return ResultVO.success(Map.of());
                })
                .defaultIfEmpty(ResultVO.error(ResultVO.UNAUTHORIZED, "用户名密码不匹配！"));
    }

    /**
     * 获取admin信息
     * @param uid
     * @return
     */
    @GetMapping("/info")
    public Mono<ResultVO> getInfo(@RequestAttribute("uid") long uid) {
        return userService.getUser(uid)
                .flatMap(user -> Mono.just(ResultVO.success(Map.of("user",user))));
    }

    /**
     * 修改信息
     * @param user
     * @param uid
     * @return
     */
    @PostMapping("/info")
    public Mono<ResultVO> postInfo(@RequestBody User user,@RequestAttribute("uid") long uid) {
        return userService.resetInfo(user, uid)
                .then(Mono.just(ResultVO.success("修改成功！")));
    }

    /**
     * 重置密码
     * @param pwd
     * @param uid
     * @return
     */
    @PutMapping("/password/{pwd}")
    public Mono<ResultVO> putPassword(@PathVariable String pwd, @RequestAttribute("uid") long uid) {
        return userService.resetPassword(pwd, uid)
                .then(Mono.just(ResultVO.success("修改成功！")));
    }
}
