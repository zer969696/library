package ru.benzoback.library.security;

import liquibase.Liquibase;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.benzoback.library.service.UserAccountService;

import java.util.List;
import java.util.Properties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired @Qualifier("appUserDetailsService")
    UserDetailsService userDetailsService;
    @Autowired
    UserAccountService userAccountService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        Properties users = new Properties();
        List<String> userLogins = userAccountService.findAllLogins();
        UserDetails tmpUser;

        for(String login : userLogins) {
            tmpUser = userDetailsService.loadUserByUsername(login);
            users.put(tmpUser.getUsername(),
                    tmpUser.getPassword() + ',' + tmpUser.getAuthorities() + ',' + tmpUser.isEnabled());
        }

        return new InMemoryUserDetailsManager(users);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //static content load + h2-console frame allowing
        http.headers().cacheControl();
        http.headers().frameOptions().disable();

        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic().realmName("library").authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }
}
