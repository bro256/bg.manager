package javau7.bg.manager.repositories;

import java.util.Optional;

import javau7.bg.manager.models.ERole;
import javau7.bg.manager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

