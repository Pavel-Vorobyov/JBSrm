package com.pavel.jbsrm.common.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private String userName;
    private String token;
    private Long id;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetails(String userName, long id, String token, List<GrantedAuthority> grantedAuthorities) {

        this.userName = userName;
        this.id = id;
        this.token = token;
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
