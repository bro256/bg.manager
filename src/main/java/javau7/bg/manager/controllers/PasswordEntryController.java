package javau7.bg.manager.controllers;

import javau7.bg.manager.models.PasswordEntry;
import javau7.bg.manager.services.PasswordEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/password-entries")
public class PasswordEntryController {

    private final PasswordEntryService passwordEntryService;

    @Autowired
    public PasswordEntryController(PasswordEntryService passwordEntryService){
        this.passwordEntryService = passwordEntryService;
    }

    @GetMapping
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntries() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntries();
        return ResponseEntity.ok(passwordEntries);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntriesInFavorites() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntriesInFavorites();
        return ResponseEntity.ok(passwordEntries);
    }

    @GetMapping("/trash")
    public ResponseEntity<List<PasswordEntry>> getAllPasswordEntriesInTrash() {
        List<PasswordEntry> passwordEntries = passwordEntryService.getAllPasswordEntriesInTrash();
        return ResponseEntity.ok(passwordEntries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasswordEntry> getPasswordEntryById(@PathVariable Long id) {
        PasswordEntry passwordEntry = passwordEntryService.getPasswordEntryById(id);
        return ResponseEntity.ok(passwordEntry);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PasswordEntry> createPasswordEntry(@RequestBody PasswordEntry passwordEntry) {
        PasswordEntry createdPasswordEntry = passwordEntryService.createPasswordEntry(passwordEntry);
        return ResponseEntity.ok(createdPasswordEntry);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<PasswordEntry> updatePasswordEntry(@PathVariable Long id, @RequestBody PasswordEntry updatedPasswordEntry) {
        PasswordEntry updatedEntry = passwordEntryService.updatePasswordEntry(id, updatedPasswordEntry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasswordEntry(@PathVariable Long id) {
        passwordEntryService.deletePasswordEntry(id);
        return ResponseEntity.noContent().build();
    }

}
