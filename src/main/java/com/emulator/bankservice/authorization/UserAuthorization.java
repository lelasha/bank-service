package com.emulator.bankservice.authorization;

import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.entity.UserEntity;

public interface UserAuthorization {
    boolean authenticate(String actualCredential, UserEntity userEntity);
    AuthMethod getType();
}
