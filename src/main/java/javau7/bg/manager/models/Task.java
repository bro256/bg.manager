package javau7.bg.manager.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String content;

    @Column
    private String note;

    @Column
    private LocalDate start;

    @Column
    private LocalDate finish;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @Column(nullable = false, columnDefinition = "integer default 2")
    private int priority;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Column(nullable = false)
    private boolean is_read;

    @Column(nullable = false)
    private LocalDateTime read_at;

    public boolean isOverdue() {
        return finish != null && LocalDate.now().isAfter(finish) && status < 2;
    }

    public Task() {
    }

    public Task(String content, String note, LocalDate start, LocalDate finish, User owner, User assignee, int priority, int status, LocalDateTime created_at, LocalDateTime updated_at, boolean is_read, LocalDateTime read_at) {
        this.content = content;
        this.note = note;
        this.start = start;
        this.finish = finish;
        this.owner = owner;
        this.assignee = assignee;
        this.priority = priority;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_read = is_read;
        this.read_at = read_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getFinish() {
        return finish;
    }

    public void setFinish(LocalDate finish) {
        this.finish = finish;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }

    public LocalDateTime getRead_at() {
        return read_at;
    }

    public void setRead_at(LocalDateTime read_at) {
        this.read_at = read_at;
    }



}
