package com.pavel.jbsrm.common.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class UserPrinciple implements UserDetails {

    @Getter
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    @Getter
    private UserRole userRole;

    private Collection<? extends GrantedAuthority> authorities;

    private UserPrinciple(Long id, String email, String password, UserRole userRole, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().name()));

        return new UserPrinciple(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUserRole(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
