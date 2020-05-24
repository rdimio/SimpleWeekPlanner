package ru.mycreation.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycreation.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    <T> T findOneByName(String role_user);
}
