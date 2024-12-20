package com.makar.tenant.user.service;

import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.PrincipalLookupTable;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.user.entity.User;
import com.makar.tenant.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPrincipalLookup implements PrincipalLookup {

    private final UserRepository userRepository;

    @Override
    public Optional<UserPrincipal> find(Long id) {
        return userRepository.findById(id)
                .map(this::buildPrincipal);
    }

    @Override
    public Optional<UserPrincipal> find(String username) {
        return userRepository.findByUsername(username)
                .map(this::buildPrincipal);
    }

    private UserPrincipal buildPrincipal(User user) {
        return new UserPrincipal(user.id(), Credentials.from(user.username(), user.password()), lookupTable());
    }

    @Override
    public PrincipalLookupTable lookupTable() {
        return PrincipalLookupTable.USER;
    }
}
