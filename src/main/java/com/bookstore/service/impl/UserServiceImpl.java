package com.bookstore.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.PasswordRestTokenRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordRestTokenRepository passwordRestTokenRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return this.passwordRestTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user,
            final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);

        this.passwordRestTokenRepository.save(myToken);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles)
            throws Exception {
        User localUser = this.userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            throw new Exception("user already exists. Nothing will be done");
        }

        else {
            for (UserRole ur : userRoles) {
                this.roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            localUser = this.userRepository.save(user);
        }
        return localUser;
    }

}
