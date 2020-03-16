package nl.tudelft.oopp.demo.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static nl.tudelft.oopp.demo.security.SecurityConstants.EXPIRATION_TIME;
import static nl.tudelft.oopp.demo.security.SecurityConstants.HEADER_STRING;
import static nl.tudelft.oopp.demo.security.SecurityConstants.SECRET;
import static nl.tudelft.oopp.demo.security.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.tudelft.oopp.demo.entities.AppUser;
import nl.tudelft.oopp.demo.entities.Role;
import nl.tudelft.oopp.demo.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Supports custom user authentication by integrating the AppUser class together with the user roles defined in Role.
 * Uses the Spring security class User to store the user credentials and permissions.
 * Has custom implementations to the attemptAuthentication and successfulAuthentication.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            AppUser appUser = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (userRepository.existsById(appUser.getEmail())) {
                AppUser repositoryAppUser = userRepository.findByEmail(appUser.getEmail());
                for (Role role: repositoryAppUser.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                repositoryAppUser.setLoggedIn(true);
                userRepository.save(repositoryAppUser);
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword(), authorities));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth)  {
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
