package uz.teamsupercool.finishit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import uz.teamsupercool.finishit.model.Comment;
import uz.teamsupercool.finishit.model.Task;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

import java.util.Objects;

@RestController
@RequestMapping("api/doc/{id}/comment")
public class CommentController {
    final UserService userService;
    final DocService docService;

    public CommentController(UserService userService, DocService docService) {
        this.userService = userService;
        this.docService = docService;
    }

    @PostMapping
    public ResponseEntity<Object> addComment(@PathVariable String id, @RequestBody Comment comment) {
        if (!Objects.nonNull(comment.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            docService.addComment(id, comment);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> updateComment(@PathVariable String id, @PathVariable String commentId, @RequestBody Comment comment) {
        if (!Objects.nonNull(comment.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            try {
                docService.updateComment(id, commentId, comment);
                return ResponseEntity.ok().build();
            } catch (RuntimeException exception) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable String id, @PathVariable String commentId) {
        return UserDocUtils.deleteDocContent(userService, docService, id, commentId);
    }
}
