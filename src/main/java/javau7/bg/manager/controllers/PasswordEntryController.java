package javau7.bg.manager.controllers;

import javau7.bg.manager.models.PasswordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<PasswordEntry> getPasswordEntryById(@PathVariable Long id) {
        PasswordEntry passwordEntry = passwordEntryService.getPasswordEntryById(id);
        return ResponseEntity.ok(passwordEntry);
    }

    @PostMapping
    public ResponseEntity<PasswordEntry> createPasswordEntry(@RequestBody PasswordEntry passwordEntry) {
        PasswordEntry createdPasswordEntry = passwordEntryService.createPasswordEntry(passwordEntry);
        return ResponseEntity.ok(createdPasswordEntry);
    }

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