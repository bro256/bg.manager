package javau7.bg.manager.repositories;

import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordEntryRepository extends JpaRepository<PasswordEntry, Long> {

    List<PasswordEntry> findAllByOwnerUsernameOrderByTitleAsc(String username);
}
