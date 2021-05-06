package ru.moore.market.firstproject.core.models.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RedisHash("token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityToken {

    @Id
    private String id;

    private String token;

    @TimeToLive
    private long timeToLive;

}
