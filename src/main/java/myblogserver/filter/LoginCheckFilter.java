//package myblogserver.filter;
//
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import myblogserver.exception.XException;
//import myblogserver.utils.JwtUtil;
//import myblogserver.utils.ResultVO;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//
//@Component
//@Slf4j
//public class LoginCheckFilter implements WebFilter {
//
//    // 路径匹配器
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    String[] urls = new String[] {
//            "/api/admin/login",
//    };
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getPath().pathWithinApplication().value();
//
//        for (String url : urls) {
//            boolean match = PATH_MATCHER.match(url, path);
//            if(match) {
//                return chain.filter(exchange);
//            } else if (path.contains("/api/front")) {
//                return chain.filter(exchange);
//            }
//        }
//
//        String token = request.getHeaders().getFirst("token");
//        if(token != null) {
//            try {
//                Claims claims = JwtUtil.parseJWT(token);
//                String uid = claims.getId();
//                exchange.getAttributes().put("uid", uid);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            return chain.filter(exchange);
//        }
//
//        throw new XException(ResultVO.UNAUTHORIZED, "未登录");
//    }
//}

package myblogserver.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class LoginCheckFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Allow all requests without any checks
        return chain.filter(exchange);
    }
}
