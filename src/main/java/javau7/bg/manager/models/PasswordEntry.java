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

    @Lob
    @Column(name = "auth_tag", length = 16)
    private byte[] authTag;

    @Column(name = "website", length = 250)
    private String website;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_in_bookmarks")
    private boolean isInBookmarks;

    @Column(name = "is_in_trash")
    private boolean isInTrash;


    public PasswordEntry() {
    }

    public PasswordEntry(User owner, String title, String username, byte[] encryptedPassword, byte[] encryptionIv, byte[] authTag, String website, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isInBookmarks, boolean isInTrash) {
        this.owner = owner;
        this.title = title;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.encryptionIv = encryptionIv;
        this.authTag = authTag;
        this.website = website;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isInBookmarks = isInBookmarks;
        this.isInTrash = isInTrash;
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

    public byte[] getAuthTag() {
        return authTag;
    }

    public void setAuthTag(byte[] authTag) {
        this.authTag = authTag;
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

    public boolean isInBookmarks() {
        return isInBookmarks;
    }

    public void setInBookmarks(boolean inBookmarks) {
        isInBookmarks = inBookmarks;
    }

    public boolean isInTrash() {
        return isInTrash;
    }

    public void setInTrash(boolean inTrash) {
        isInTrash = inTrash;
    }
}

