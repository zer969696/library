package ru.benzoback.library.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.benzoback.library.service.UserAccountService;
import ru.benzoback.library.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthenticationService tokenAuthenticationService;

    public JwtLoginFilter(String url, AuthenticationManager authenticationManager, UserService userService, UserAccountService userAccountService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
        tokenAuthenticationService = new TokenAuthenticationService(userService, userAccountService);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException {
//        StringBuffer jb = new StringBuffer();
//        String line = null;
//        try {
//            BufferedReader reader = httpServletRequest.getReader();
//            while ((line = reader.readLine()) != null)
//                jb.append(line);
//        } catch (Exception e) { /*report an error*/ }
//        JSONObject jsonObject;
//        try {
//            jsonObject = HTTP.toJSONObject(jb.toString());
//        } catch (JSONException e) {
//            // crash and burn
//            throw new IOException("Error parsing JSON request string");
//        }
//
//        String log = jsonObject.getString("login");
//        String pas = jsonObject.getString("password");
        AccountCredentials credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        String name = authentication.getName();
        tokenAuthenticationService.addAuthentication(response,name);
    }
}

