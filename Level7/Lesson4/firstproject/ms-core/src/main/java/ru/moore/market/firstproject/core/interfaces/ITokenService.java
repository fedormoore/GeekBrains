package ru.moore.market.firstproject.core.interfaces;

import ru.moore.market.firstproject.core.models.entities.UserInfo;

public interface ITokenService {

    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
