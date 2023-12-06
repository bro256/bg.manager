package javau7.bg.manager.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_entries")
public class PasswordEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private User owner;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "username", length = 50)
    private String username;

    @Lob
    @Column(name = "encrypted_password", length = 50)
    private byte[] encryptedPassword;

    @Lob
    @Column(name = "encryption_iv", length = 32)
    private byte[] encryptionIv;

    @Column(name = "website", length = 250)
    private String website;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "in_favorites")
    private boolean inFavorites;

    @Column(name = "in_trash")
    private boolean inTrash;


    public PasswordEntry() {
    }

    public PasswordEntry(User owner, String title, String username, byte[] encryptedPassword, byte[] encryptionIv, byte[] authTag, String website, LocalDateTime createdAt, LocalDateTime updatedAt, boolean inFavorites, boolean inTrash) {
        this.owner = owner;
        this.title = title;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.encryptionIv = encryptionIv;
        this.website = website;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.inFavorites = inFavorites;
        this.inTrash = inTrash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public byte[] getEncryptionIv() {
        return encryptionIv;
    }

    public void setEncryptionIv(byte[] encryptionIv) {
        this.encryptionIv = encryptionIv;
    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isInFavorites() {
        return inFavorites;
    }

    public void setInFavorites(boolean inFavorites) {
        this.inFavorites = inFavorites;
    }

    public boolean isInTrash() {
        return inTrash;
    }

    public void setInTrash(boolean inTrash) {
        this.inTrash = inTrash;
    }
}

