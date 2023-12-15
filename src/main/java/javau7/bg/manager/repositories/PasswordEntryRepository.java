package javau7.bg.manager.repositories;

import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing password entries.
 */
public interface PasswordEntryRepository extends JpaRepository<PasswordEntry, Long> {

    /**
     * Find all password entries for a specific owner, ordered by title in ascending order.
     */
    List<PasswordEntry> findAllByOwnerUsernameOrderByTitleAsc(String username);

    /**
     * Find all password entries for a specific owner that are marked as favorites or in the trash,
     * ordered by title in ascending order.
     */
    List<PasswordEntry> findAllByOwnerUsernameAndInFavoritesAndInTrashOrderByTitleAsc(String username, boolean inFavorites, boolean inTrash);

    /**
     * Find all password entries for a specific owner that are in the trash, ordered by title in ascending order.
     */
    List<PasswordEntry> findAllByOwnerUsernameAndInTrashOrderByTitleAsc(String username, boolean inTrash);


}
