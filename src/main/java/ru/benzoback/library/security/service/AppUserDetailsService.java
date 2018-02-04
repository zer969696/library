package ru.benzoback.library.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.benzoback.library.security.dao.AppUserDao;
import ru.benzoback.library.security.model.AppUser;
import ru.benzoback.library.service.UserAccountService;

import java.util.ArrayList;
import java.util.List;

@Service("appUserDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserAccountService userAccountService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        AppUser appUser = new AppUserDao(userAccountService).getAppUser(login);

        if (appUser == null) {
            throw new UsernameNotFoundException("no such user");
        }

        return new org.springframework.security.core.userdetails.User(
                appUser.getLogin(),
                appUser.getPassword(),
                true,
                true,
                true,
                true,
                getGrantedAuthorities()
        );
    }

    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }
}
