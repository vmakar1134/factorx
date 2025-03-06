package com.makar.tenant.user;

import com.makar.tenant.security.Authenticator;
import com.makar.tenant.security.JwtTokenPair;
import com.makar.tenant.security.PrincipalLookup;
import com.makar.tenant.security.UserPrincipal;
import com.makar.tenant.user.rest.model.LoginRequest;
import com.makar.tenant.user.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Authenticator authenticator;

    private final PrincipalLookup principalLookup;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public void register(RegistrationRequest request) {
        final String encodedPassword = passwordEncoder.encode(request.password());
        var user = new User(null, request.email(), null, null, encodedPassword, request.role());
        userRepository.save(user);
    }

    public JwtTokenPair login(LoginRequest request) {
        return principalLookup.locate(request.email())
                .map(principal -> authenticator.authenticate(principal, request.password()))
                .orElseThrow(() -> new IllegalArgumentException("User with email " + request.email() + " not found"));
    }

    public JwtTokenPair refresh(String refreshToken) {
        return authenticator.refresh(refreshToken);
    }

    public void logout(String jwt) {
        authenticator.logout(jwt);
    }

    public void logout() {
        var principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authenticator.logout(principal);
    }

}
