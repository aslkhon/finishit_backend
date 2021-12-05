/*
 * FINISH.IT Task Manager
 * Final project of Application Programming in Java Course | Fall 2021
 *
 * Developed by TeamSuperCool:
 *
 * Aslkhon Khoshimkhujaev U2010145
 * Dilmurod Sagatov U2010235
 * Saidamalkhon Inoyatov U2010093
 * David Suleymanov U2010271
 * */

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

    /*
    * Add Comment
    *
    * requires {
    *   content: String
    * }
    *
    * returns 201 CREATED status
    * */
    @PostMapping
    public ResponseEntity<Object> addComment(@PathVariable String id, @RequestBody Comment comment) {
        if (!Objects.nonNull(comment.getContent())) {
            return ResponseEntity.badRequest().build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            return new ResponseEntity<>(docService.addComment(id, comment), HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Update Comment
     *
     * requires {
     *   content: String
     * }
     *
     * returns 200 OK status
     * */
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

    /*
     * Delete Comment
     *
     * requires {
     *   content: String
     * }
     *
     * returns 204 NO CONTENT status
     * */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable String id, @PathVariable String commentId) {
        return UserDocUtils.deleteDocContent(userService, docService, id, commentId);
    }
}
