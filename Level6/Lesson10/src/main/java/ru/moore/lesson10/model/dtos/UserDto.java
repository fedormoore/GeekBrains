package ru.moore.lesson10.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.moore.lesson10.model.entities.Product;
import ru.moore.lesson10.model.entities.User;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String login;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
    }
}
