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




    private String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            // Handle the case where principal is not an instance of UserDetails
            return null;
        }
    }

}
