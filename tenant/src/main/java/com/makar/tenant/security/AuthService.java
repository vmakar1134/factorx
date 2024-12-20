package com.makar.tenant.security;

import com.makar.tenant.supervisor.rest.model.LoginSupervisorRequest;
import com.makar.tenant.supervisor.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public abstract class AuthService {

    protected final Authenticator authenticator;

    protected final PrincipalLookup principalLookup;

    protected abstract void saveEntity(Credentials credentials);

    public void register(RegistrationRequest registrationRequest) {
        var credentials = authenticator.register(registrationRequest.email(), registrationRequest.password());
        saveEntity(credentials);
    }

    public JwtTokenPair login(LoginSupervisorRequest request) {
        var principal = principalLookup.get(request.email());
        return authenticator.authenticate(principal, request.password());
    }

    public JwtTokenPair refresh(String refreshToken) {
        return authenticator.refresh(refreshToken);
    }

    public void logout(String jwt) {
        authenticator.logout(jwt);
    }

    public void logout() {
        var principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principalLookup.find(principal.getId())
            .ifPresentOrElse(authenticator::logout, () -> {
                throw new IllegalArgumentException("Principal not found");
            });
    }

}
