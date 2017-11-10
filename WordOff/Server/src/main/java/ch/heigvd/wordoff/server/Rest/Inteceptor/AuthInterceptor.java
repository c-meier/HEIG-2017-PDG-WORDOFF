package ch.heigvd.wordoff.server.Rest.Inteceptor;

import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor is enabled for all request path in the RestConfig class.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Find user associated with authentification

        boolean trusted = false;

        String token = request.getHeader(SecurityConst.AUTH_HEADER);
        if (token != null) {
            // parse the token.
            Claims claims = Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.decode(SecurityConst.TOKEN_SECRET))
                    .parseClaimsJws(token)
                    //.parseClaimsJws(token.replace(SecurityConst.TOKEN_PREFIX, ""))
                    .getBody();

            String login = claims.get("login", String.class);

            if (login != null) {
                User user = userRepository.findByCredentialsLogin(login);
                if(user != null) {
                    request.setAttribute("player", user);
                    trusted = true;
                }
            }
        }

        if(!trusted) {
            response.sendError(401);
        }
        return trusted;
    }
}
