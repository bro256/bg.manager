package javau7.bg.manager.services;

import javau7.bg.manager.exceptions.UnauthorizedOperationException;
import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.models.User;
import javau7.bg.manager.repositories.PasswordEntryRepository;
import javau7.bg.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * Service class for managing password entries.
 */
@Service
public class PasswordEntryService {
    private final PasswordEntryRepository passwordEntryRepository;
    private final UserRepository userRepository;


    /**
     * Constructor for PasswordEntryService.
     *
     * @param passwordEntryRepository The repository for password entries.
     * @param userRepository          The repository for users.
     */
    @Autowired
    public PasswordEntryService(PasswordEntryRepository passwordEntryRepository,
                                UserRepository userRepository) {
        this.passwordEntryRepository = passwordEntryRepository;
        this.userRepository = userRepository;
    }


    /**
     * Get all password entries for the currently logged-in user.
     *
     * @return List of password entries.
     */
    public List<PasswordEntry> getAllPasswordEntries() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameOrderByTitleAsc(username);
    }


    /**
     * Get all password entries marked as favorites for the currently logged-in user.
     *
     * @return List of favorite password entries.
     */
    public List<PasswordEntry> getAllPasswordEntriesInFavorites() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameAndInFavoritesAndInTrashOrderByTitleAsc(username, true, false);
    }


    /**
     * Get all password entries in the trash for the currently logged-in user.
     *
     * @return List of password entries in the trash.
     */
    public List<PasswordEntry> getAllPasswordEntriesInTrash() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameAndInTrashOrderByTitleAsc(username, true);
    }


    /**
     * Toggle a password entry's trash status.
     *
     * @param id The ID of the password entry to toggle.
     * @return The updated password entry.
     */
    public PasswordEntry togglePasswordEntryInTrash(Long id) {
        PasswordEntry passwordEntry = getPasswordEntryById(id);
        checkAuthorization(passwordEntry);
        passwordEntry.setInTrash(!passwordEntry.isInTrash());
        return passwordEntryRepository.save(passwordEntry);
    }

    /**
     * Toggle a password entry's favorites status.
     *
     * @param id The ID of the password entry to toggle.
     * @return The updated password entry.
     */
    public PasswordEntry togglePasswordEntryInFavorites(Long id){
        PasswordEntry passwordEntry = getPasswordEntryById(id);
        checkAuthorization(passwordEntry);
        passwordEntry.setInFavorites(!passwordEntry.isInFavorites());
        return passwordEntryRepository.save(passwordEntry);
    }


    /**
     * Get a password entry by id.
     *
     * @param id The ID of the password entry to get.
     * @return The password entry with id.
     */
    public PasswordEntry getPasswordEntryById(Long id){
        Optional<PasswordEntry> optionalPasswordEntry = passwordEntryRepository.findById(id);
        if (optionalPasswordEntry.isPresent()) {
            PasswordEntry passwordEntry = optionalPasswordEntry.get();
            checkAuthorization(passwordEntry);
            return passwordEntry;
        } else {
            throw new NoSuchElementException("PasswordEntry not found with id: " + id);
        }
    }


    /**
     * Create password entry.
     *
     * @return Created password entry.
     */
    public PasswordEntry createPasswordEntry(PasswordEntry passwordEntry) {
        String loggedInUsername = getLoggedInUsername();
        Optional<User> optionalOwner = userRepository.findByUsername(loggedInUsername);
        User owner = optionalOwner.orElseThrow(() -> new NoSuchElementException("User not found with username: " + loggedInUsername));
        passwordEntry.setOwner(owner);

        passwordEntry.setCreatedAt(LocalDateTime.now());
        passwordEntry.setUpdatedAt(LocalDateTime.now());

        passwordEntry.setInFavorites(passwordEntry.isInFavorites());
        passwordEntry.setInTrash(false);

        return passwordEntryRepository.save(passwordEntry);
    }


    /**
     * Update password entry.
     *
     * @param id The ID of the password entry to edit.
     * @return The updated password entry.
     */
    public PasswordEntry updatePasswordEntry(Long id, PasswordEntry updatedPasswordEntry) {
        Optional<PasswordEntry> optionalExistingEntry = passwordEntryRepository.findById(id);

        if (optionalExistingEntry.isPresent()) {
            PasswordEntry existingEntry = optionalExistingEntry.get();

            String loggedInUsername = getLoggedInUsername();

            checkAuthorization(existingEntry);

            Optional<User> optionalOwner = userRepository.findByUsername(loggedInUsername);
            User owner = optionalOwner.orElseThrow(() -> new NoSuchElementException("User not found with username: " + loggedInUsername));
            existingEntry.setOwner(owner);

            existingEntry.setTitle(updatedPasswordEntry.getTitle());
            existingEntry.setUsername(updatedPasswordEntry.getUsername());
            existingEntry.setEncryptedPassword(updatedPasswordEntry.getEncryptedPassword());
            existingEntry.setEncryptionIv(updatedPasswordEntry.getEncryptionIv());
            existingEntry.setWebsite(updatedPasswordEntry.getWebsite());
            existingEntry.setInFavorites(updatedPasswordEntry.isInFavorites());
            existingEntry.setInTrash(updatedPasswordEntry.isInTrash());
            existingEntry.setCategory(updatedPasswordEntry.getCategory());

            // Set the updated_at timestamp
            existingEntry.setUpdatedAt(LocalDateTime.now());

            return passwordEntryRepository.save(existingEntry);
        } else {
            throw new NoSuchElementException("PasswordEntry not found with id: " + id);
        }
    }


    /**
     * Delete password entry.
     *
     * @param id The ID of the password entry to delete.
     */
    public void deletePasswordEntry (Long id){
        Optional<PasswordEntry> optionalPasswordEntry = passwordEntryRepository.findById(id);

        if (optionalPasswordEntry.isPresent()){
            PasswordEntry passwordEntry = optionalPasswordEntry.get();

            String loggedInUsername = getLoggedInUsername();

            checkAuthorization(passwordEntry);
            passwordEntryRepository.delete(passwordEntry);

        } else {
            throw new NoSuchElementException("PasswordEntry not found with id: " + id);
        }
    }


    /**
     * Get the currently logged-in user's username.
     *
     * @return The username of the logged-in user.
     */
    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return null;
        }
    }


    /**
     * Check if the currently logged-in user is authorized to perform an operation on a password entry.
     *
     * @param passwordEntry The password entry to check authorization for.
     * @throws UnauthorizedOperationException If the user is not authorized.
     */
    private void checkAuthorization(PasswordEntry passwordEntry) {
        String loggedInUsername = getLoggedInUsername();
        if (!passwordEntry.getOwner().getUsername().equals(loggedInUsername)) {
            throw new UnauthorizedOperationException("You do not have permission to perform this action on PasswordEntry with id: " + passwordEntry.getId());
        }
    }
}
