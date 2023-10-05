package com.teamvoy.services;

import com.teamvoy.entity.User;
import com.teamvoy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user with username: " + username);

        Optional<User> optionalUserEntity = Optional.ofNullable(userRepository
                .findByUsername(username));
        if (optionalUserEntity.isEmpty()) {
            logger.warn("User with username: " + username + " not found");
                throw new UsernameNotFoundException(
                        "User with username: " + username + " not found");
        } else {
            User user = optionalUserEntity.get();
            logger.info("User loaded: " + user.getUsername());
            return user;
        }
    }

}