package javau7.bg.manager.controllers;

import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.services.PasswordEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing password entries.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/password-entries")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class PasswordEntryController {

    private final PasswordEntryService passwordEntryService;

    /**
     * Constructor for PasswordEntryController.
     */
    @Autowired
    public PasswordEntryController(PasswordEntryService passwordEntryService){
        this.passwordEntryService = passwordEntryService;
    }


    /**
     * Get a password entry by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PasswordEntry> getPasswordEntryById(@PathVariable Long id) {
        PasswordEntry passwordEntry = passwordEntryService.getPasswordEntryById(id);
        return ResponseEntity.ok(passwordEntry);
    }

    /**
     * Get all password entries.
     */
    @GetMapping
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntries() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntries();
        return ResponseEntity.ok(passwordEntries);
    }

    /**
     * Create a password entry.
     */
    @PostMapping
    public ResponseEntity<PasswordEntry> createPasswordEntry(@RequestBody PasswordEntry passwordEntry) {
        PasswordEntry createdPasswordEntry = passwordEntryService.createPasswordEntry(passwordEntry);
        return ResponseEntity.ok(createdPasswordEntry);
    }

    /**
     * Update a password entry by id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PasswordEntry> updatePasswordEntry(@PathVariable Long id, @RequestBody PasswordEntry updatedPasswordEntry) {
        PasswordEntry updatedEntry = passwordEntryService.updatePasswordEntry(id, updatedPasswordEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    /**
     * Delete a password entry by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasswordEntry(@PathVariable Long id) {
        passwordEntryService.deletePasswordEntry(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all password entries in favorites.
     */
    @GetMapping("/favorites")
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntriesInFavorites() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntriesInFavorites();
        return ResponseEntity.ok(passwordEntries);
    }

    /**
     * Get all password entries in trash.
     */
    @GetMapping("/trash")
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntriesInTrash() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntriesInTrash();
        return ResponseEntity.ok(passwordEntries);
    }

    /**
     * Toggle a password in trash.
     */
    @PutMapping("/trash/{id}")
    public ResponseEntity<PasswordEntry> togglePasswordEntryInTrash(@PathVariable Long id) {
        PasswordEntry passwordEntry = passwordEntryService.togglePasswordEntryInTrash(id);
        return ResponseEntity.ok(passwordEntry);
    }
}
