package ru.moore.market.core.interfaces;

import ru.moore.market.core.models.entities.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
