package ru.mycreation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByLogin(String login);

}