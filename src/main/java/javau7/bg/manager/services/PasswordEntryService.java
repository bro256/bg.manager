package javau7.bg.manager.services;

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
        return passwordEntryRepository.findAllByOwnerUsername(username);
    }


    public PasswordEntry getPasswordEntryById(Long id){
        Optional<PasswordEntry> optionalPasswordEntry = passwordEntryRepository.findById(id);
        return optionalPasswordEntry.orElse(null);
    }


    public PasswordEntry createPasswordEntry(PasswordEntry passwordEntry) {
        String loggedInUsername = getLoggedInUsername();
        Optional<User> optionalOwner = userRepository.findByUsername(loggedInUsername);
        User owner = optionalOwner.orElseThrow(() -> new NoSuchElementException("User not found with username: " + loggedInUsername));
        passwordEntry.setOwner(owner);

        passwordEntry.setCreatedAt(LocalDateTime.now());
        passwordEntry.setUpdatedAt(LocalDateTime.now());

        return passwordEntryRepository.save(passwordEntry);
    }


    public PasswordEntry updatePasswordEntry(Long id, PasswordEntry updatedPasswordEntry) {
        Optional<PasswordEntry> optionalExistingEntry = passwordEntryRepository.findById(id);

        if (optionalExistingEntry.isPresent()) {
            PasswordEntry existingEntry = optionalExistingEntry.get();

            // Check if the logged-in user is the owner of the existing entry
            String loggedInUsername = getLoggedInUsername();
//            if (!existingEntry.getOwner().getUsername().equals(loggedInUsername)) {
//                throw new UnauthorizedOperationException("You do not have permission to update this PasswordEntry");
//                // UnauthorizedOperationException should be a custom exception class you create
//            }

            Optional<User> optionalOwner = userRepository.findByUsername(loggedInUsername);
            User owner = optionalOwner.orElseThrow(() -> new NoSuchElementException("User not found with username: " + loggedInUsername));
            existingEntry.setOwner(owner);

            existingEntry.setTitle(updatedPasswordEntry.getTitle());
            existingEntry.setUsername(updatedPasswordEntry.getUsername());
            existingEntry.setEncryptedPassword(updatedPasswordEntry.getEncryptedPassword());
            existingEntry.setEncryptionIv(updatedPasswordEntry.getEncryptionIv());
            existingEntry.setAuthTag(updatedPasswordEntry.getAuthTag());
            existingEntry.setWebsite(updatedPasswordEntry.getWebsite());
            existingEntry.setInBookmarks(updatedPasswordEntry.isInBookmarks());
            existingEntry.setInTrash(updatedPasswordEntry.isInTrash());

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

            //    if (!existingEntry.getOwner().getUsername().equals(loggedInUsername)) {
//                throw new UnauthorizedOperationException("You do not have permission to update this PasswordEntry");
//                // UnauthorizedOperationException should be a custom exception class you create
//            }
            passwordEntryRepository.delete(passwordEntry);

        } else {
            throw new NoSuchElementException("PasswordEntry not found with id: " + id);
        }

    }



    private String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return null;
        }
    }

}
