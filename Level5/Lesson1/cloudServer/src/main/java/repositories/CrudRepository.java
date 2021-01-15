package repositories;

public interface CrudRepository<T> {

    T save(T object);

    void update(T object);

}
