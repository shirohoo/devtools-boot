package io.github.shirohoo.devtools.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DevtoolsUserDetailsService implements UserDetailsService {
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByUsername(username).orElseThrow();
        String password = manager.getPassword();
        List<GrantedAuthority> grantedAuthorities = manager.getGrantedAuthorities();
        return new User(username, password, grantedAuthorities);
    }
}
