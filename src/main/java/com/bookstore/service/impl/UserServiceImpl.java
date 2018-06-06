package com.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.repository.PasswordRestTokenRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

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

}
