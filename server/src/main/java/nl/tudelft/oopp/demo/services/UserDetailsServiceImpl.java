package nl.tudelft.oopp.demo.services;

import java.util.ArrayList;
import java.util.Collection;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Is necessary as a part of user authentication / authorization.
 * Implements the UserDetailsService interface.
 * Adds an implementation to the loadByUsername method.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(email);
        if (appUser == null) {
            throw new UsernameNotFoundException(email);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: appUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }
}