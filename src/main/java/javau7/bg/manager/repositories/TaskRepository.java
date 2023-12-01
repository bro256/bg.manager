package javau7.bg.manager.repositories;

import javau7.bg.manager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
