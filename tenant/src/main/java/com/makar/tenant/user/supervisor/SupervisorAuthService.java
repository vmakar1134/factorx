
package com.makar.tenant.user.supervisor;

import com.makar.tenant.security.AuthService;
import com.makar.tenant.security.Credentials;
import com.makar.tenant.user.User;
import com.makar.tenant.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.makar.tenant.security.UserRole.SUPERVISOR;

@Service
@RequiredArgsConstructor
public class SupervisorAuthService extends AuthService {

    private final UserRepository userRepository;

    @Override
    protected void saveEntity(Credentials credentials) {
        var admin = new User(null, credentials.username(), null, null, credentials.password(), SUPERVISOR);
        userRepository.save(admin);
    }

}
