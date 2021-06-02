package ru.moore.market.firstproject.core.interfaces;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import ru.moore.market.firstproject.core.models.entities.SecurityToken;

@Repository
public interface TokenRepository extends KeyValueRepository<SecurityToken, String> {

}
