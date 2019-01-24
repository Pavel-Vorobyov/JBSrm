package com.pavel.jbsrm.auth.security;

import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmail(userEmail)
                .map(UserPrinciple::build)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with -> email : " + userEmail));
    }
}
