package uz.teamsupercool.finishit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.teamsupercool.finishit.model.Task;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

import java.util.Objects;

@RestController
@RequestMapping("api/doc/{id}/task")
public class TaskController {
    final UserService userService;
    final DocService docService;

    public TaskController(UserService userService, DocService docService) {
        this.userService = userService;
        this.docService = docService;
    }

    @PostMapping
    public ResponseEntity<Object> addTask(@PathVariable String id, @RequestBody Task task) {
        if (!Objects.nonNull(task.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            docService.addTask(id, task);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable String id, @PathVariable String taskId, @RequestBody Task task) {
        if (!Objects.nonNull(task.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            try {
                docService.updateTask(id, taskId, task);
                return ResponseEntity.ok().build();
            } catch (RuntimeException exception) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(@PathVariable String id, @PathVariable String taskId) {
        return UserDocUtils.deleteDocContent(userService, docService, id, taskId);
    }

    @PutMapping("/{taskId}/do")
    public ResponseEntity<Object> markTaskDone(@PathVariable String id, @PathVariable String taskId) {
        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            try {
                docService.setStatus(id, taskId, true);
                return ResponseEntity.ok().build();
            } catch (RuntimeException exception) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{taskId}/undo")
    public ResponseEntity<Object> markTaskNotDone(@PathVariable String id, @PathVariable String taskId) {
        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            try {
                docService.setStatus(id, taskId, false);
                return ResponseEntity.ok().build();
            } catch (RuntimeException exception) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
