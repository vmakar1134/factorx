package com.makar.tenant.security;

import com.makar.tenant.user.supervisor.rest.model.LoginSupervisorRequest;
import com.makar.tenant.user.supervisor.rest.model.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public abstract class AuthService {

    @Setter(onMethod_ = {@Autowired})
    protected Authenticator authenticator;

    @Setter(onMethod_ = {@Autowired})
    protected PrincipalLookup principalLookup;

    protected abstract void saveEntity(Credentials credentials);

    public void register(RegistrationRequest registrationRequest) {
        var credentials = authenticator.register(registrationRequest.email(), registrationRequest.password());
        saveEntity(credentials);
    }

    public JwtTokenPair login(LoginSupervisorRequest request) {
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
