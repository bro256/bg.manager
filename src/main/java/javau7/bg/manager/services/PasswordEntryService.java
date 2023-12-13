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

@Service
public class PasswordEntryService {
    private final PasswordEntryRepository passwordEntryRepository;
    private final UserRepository userRepository;

    @Autowired
    public PasswordEntryService(PasswordEntryRepository passwordEntryRepository,
                                UserRepository userRepository) {
        this.passwordEntryRepository = passwordEntryRepository;
        this.userRepository = userRepository;
    }


    public List<PasswordEntry> getAllPasswordEntries() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameOrderByTitleAsc(username);
    }

    public List<PasswordEntry> getAllPasswordEntriesInFavorites() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameAndInFavoritesAndInTrashOrderByTitleAsc(username, true, false);
    }

    public List<PasswordEntry> getAllPasswordEntriesInTrash() {
        String username = getLoggedInUsername();
        return passwordEntryRepository.findAllByOwnerUsernameAndInTrashOrderByTitleAsc(username, true);
    }

    public PasswordEntry togglePasswordEntryInTrash(Long id) {
        PasswordEntry passwordEntry = getPasswordEntryById(id);
        checkAuthorization(passwordEntry);
        passwordEntry.setInTrash(!passwordEntry.isInTrash());
        return passwordEntryRepository.save(passwordEntry);
    }


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



    public String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return null;
        }
    }


    private void checkAuthorization(PasswordEntry passwordEntry) {
        String loggedInUsername = getLoggedInUsername();
        if (!passwordEntry.getOwner().getUsername().equals(loggedInUsername)) {
            throw new UnauthorizedOperationException("You do not have permission to perform this action on PasswordEntry with id: " + passwordEntry.getId());
        }
    }

}
