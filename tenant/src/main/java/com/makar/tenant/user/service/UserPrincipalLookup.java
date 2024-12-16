package com.makar.tenant.user.service;

import com.makar.tenant.security.Credentials;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.PrincipalLookupTable;
import com.makar.tenant.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPrincipalLookup implements PrincipalLookup {

    private final UserRepository userRepository;

    @Override
    public Optional<Credentials> findCredentials(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new Credentials(user.username(), user.password()));
    }

    @Override
    public PrincipalLookupTable table() {
        return PrincipalLookupTable.USER;
    }
}
