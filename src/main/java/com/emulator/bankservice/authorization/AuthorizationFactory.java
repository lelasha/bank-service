package com.emulator.bankservice.authorization;

import com.emulator.bankservice.controller.request.AuthMethod;
import com.emulator.bankservice.exception.NoSuchAuthMethodException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class AuthorizationFactory {
    private final Map<AuthMethod, UserAuthorization> MAP;

    @Autowired
    public AuthorizationFactory(List<UserAuthorization> authorizations) {
        MAP = authorizations.stream()
                .collect(Collectors.toUnmodifiableMap(UserAuthorization::getType, Function.identity()));
    }

    public UserAuthorization getAuthorization(AuthMethod authMethod) {
        UserAuthorization bean = MAP.get(authMethod);
        return Optional.ofNullable(bean)
                .orElseThrow(() -> new NoSuchAuthMethodException("Auth method " + authMethod.name() + " is not supported"));
    }
}
