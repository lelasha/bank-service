package com.emulator.bankservice.authorization;

import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FingerPrintAuthorization implements UserAuthorization {
    private static final AuthMethod AUTH_METHOD = AuthMethod.FINGER_PRINT;


    @Override
    public AuthMethod getType() {
        return AUTH_METHOD;
    }

    @Override
    public boolean authenticate(String actualCredential, UserEntity userEntity) {
        log.info("Authenticating user using {} method",AUTH_METHOD);
        return actualCredential.equals(userEntity.getFingerPrint());
    }
}
