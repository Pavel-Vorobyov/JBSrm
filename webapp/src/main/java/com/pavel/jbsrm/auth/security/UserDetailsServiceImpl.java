package com.pavel.jbsrm.auth.security;

import com.pavel.jbsrm.auth.model.UserPrinciple;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userService;

    public UserDetailsServiceImpl(UserRepository userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userService.findByEmail(userEmail)
                .map(UserPrinciple::build)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> email : " + userEmail));
    }
}
