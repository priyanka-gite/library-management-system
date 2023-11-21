package com.novilms.librarymanagementsystem.security;

import com.novilms.librarymanagementsystem.model.User;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepos;

    public MyUserDetailsService(UserRepository repos) {
        this.userRepos = repos;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> OptionalUser = userRepos.findByEmail(username);
        if (OptionalUser.isPresent()) {
            User user = OptionalUser.get();
            return new MyUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
