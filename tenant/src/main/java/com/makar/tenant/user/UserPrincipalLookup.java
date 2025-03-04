package com.makar.tenant.user;

import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPrincipalLookup implements PrincipalLookup {

    private final UserRepository userRepository;

    private final UserPrincipalMapper userPrincipalMapper;

    @Override
    public Optional<UserPrincipal> locate(Long id) {
        return userRepository.findById(id)
                .map(userPrincipalMapper::toPrincipal);
    }

    @Override
    public Optional<UserPrincipal> locate(String email) {
        return userRepository.findByEmail(email)
                .map(userPrincipalMapper::toPrincipal);
    }

}
