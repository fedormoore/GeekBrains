package ru.moore.corelib.interfaces;

import ru.moore.corelib.models.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
