package xyz.codewithcami.simplejobapplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.codewithcami.simplejobapplication.models.ApplicationUser;
import xyz.codewithcami.simplejobapplication.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApplicationUser saveUser(ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public ApplicationUser getUser(String email) {
        log.info("Getting user with email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public ApplicationUser findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<ApplicationUser> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User found with email: {}", email);
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
