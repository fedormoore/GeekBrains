package ru.moore.server.repositories;

import ru.moore.server.models.Users;

public interface UsersRepository extends CrudRepository<Users>{

    boolean findUserByLogin(String login);

    Users findUserByLoginAndPassword(String login, String password);

}
