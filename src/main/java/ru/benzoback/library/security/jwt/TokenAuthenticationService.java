package ru.benzoback.library.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import ru.benzoback.library.security.converter.AppUserRoleConverter;
import ru.benzoback.library.security.dao.AppUserDao;
import ru.benzoback.library.security.model.AppUser;
import ru.benzoback.library.service.UserAccountService;
import ru.benzoback.library.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenAuthenticationService {

    private AppUserRoleConverter userRoleConverter = new AppUserRoleConverter();
    private UserService userService;
    private UserAccountService userAccountService;

    private int EXPIRATION_TIME = 1000 * 60 * 5 ; // 5 min

    private String secret = "ThisIsASecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";

    TokenAuthenticationService(UserService userService, UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        this.userService = userService;
    }

    void addAuthentication(HttpServletResponse response, String login) {
        AppUser customUser = new AppUserDao(userAccountService).getAppUser(login);
        String userRole = userRoleConverter.toString(userRoleConverter.convertClientRolesArray(customUser));
        // We generate a token now.
        String JWT = Jwts.builder()
                .setSubject(login)
                .setIssuer(userRole)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        Cookie authCookie = new Cookie(headerString, JWT);
        authCookie.setMaxAge(EXPIRATION_TIME);

        response.addCookie(authCookie);
        response.addHeader(headerString,tokenPrefix + " " + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request) {
        String token = null;
        //token = request.getHeader(headerString);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                token = cookie.getValue();
            }
        }
        //response.addCookie(new Cookie("Authorization", token));

        try {
            if (token != null) {
                // parse the token.
                String username = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                String role = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getIssuer();

                if (username != null) {
                    return new AuthenticatedUser(username, role);
                }
            }
        } catch (ExpiredJwtException ex) {
            // ex.printStackTrace();
        }


        return null;
    }

}
