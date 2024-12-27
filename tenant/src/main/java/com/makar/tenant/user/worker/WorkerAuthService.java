package com.makar.tenant.user.worker;

import com.makar.tenant.security.AuthService;
import com.makar.tenant.security.Credentials;
import com.makar.tenant.user.User;
import com.makar.tenant.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.makar.tenant.security.UserRole.WORKER;

@Service
@RequiredArgsConstructor
public class WorkerAuthService extends AuthService {

    private final UserRepository userRepository;

    @Override
    protected void saveEntity(Credentials credentials) {
        var admin = new User(null, credentials.username(), null, null, credentials.password(), WORKER);
        userRepository.save(admin);
    }

}
