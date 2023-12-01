package javau7.bg.manager.services;

import javau7.bg.manager.auth.response.JwtResponse;
import javau7.bg.manager.models.Task;
import javau7.bg.manager.models.User;
import javau7.bg.manager.repositories.TaskRepository;
import javau7.bg.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public Task create(Task task) {
        String currentUsername = getCurrentUsername();
        User owner = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Current user not found"));

        User assignee = userRepository.findById(task.getAssignee().getId())
                .orElseThrow(() -> new RuntimeException("Assignee not found"));


        LocalDateTime currentDateTime = LocalDateTime.now();
        task.setCreated_at(currentDateTime);
        task.setUpdated_at(currentDateTime);

        if (task.getPriority() <= 0) {
            task.setPriority(2);
        }

        if (task.getStatus() < 0) {
            task.setStatus(1);
        }


        return taskRepository.save(task);

    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }

        // Handle the case when the principal is not of the expected type
        throw new RuntimeException("Unable to retrieve the current username");
    }

//    public Task findById(Long id) {
//        // Implementation
//    }
//
//    public Task update(Long id, Task task) {
//        // Implementation
//    }
//
//    public void delete(Long id) {
//        // Implementation
//    }
}
