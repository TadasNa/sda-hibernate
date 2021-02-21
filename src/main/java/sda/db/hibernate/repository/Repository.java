package sda.db.hibernate.repository;

import java.util.List;
import java.util.UUID;

public interface Repository<T, ID> {

  //  void create(T entity);  // CREATE

    T find(ID id);          // READ

    List<T> findALL();

    void save(T entity);    // UPDATE

    void delete(T entity);  // DELETE

}
