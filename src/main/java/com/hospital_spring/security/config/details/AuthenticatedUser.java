package com.hospital_spring.security.config.details;

import com.hospital_spring.users.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class AuthenticatedUser implements UserDetails { // UserDetailsImpl
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authorityRole = new SimpleGrantedAuthority(user.getRole().name());
        SimpleGrantedAuthority authorityWorkplace = new SimpleGrantedAuthority(user.getWorkplace().name());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authorityRole);
        authorities.add(authorityWorkplace);
        return authorities;
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getWorkplace().name());
//        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isNotLocked();
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
