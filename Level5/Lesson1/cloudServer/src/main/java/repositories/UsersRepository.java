package repositories;

import models.Users;

public interface UsersRepository extends CrudRepository<Users>{

    boolean findUserByLogin(String login);

    Users findUserByLoginAndPassword(String login, String password);

}
