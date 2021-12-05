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

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.teamsupercool.finishit.model.GetUserResponse;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

import java.util.Objects;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;
    private final DocService docService;

    public UserController(UserService userService, DocService docService) { this.userService = userService; this.docService = docService; }

    /*
     * Get user info
     * */
    @GetMapping
    public ResponseEntity<Object> getUser() {
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.getUserByUsername(details.getUsername());

        return ResponseEntity.ok(new GetUserResponse(user.getId(), user.getUsername()));
    }

    /*
     * Edit user info
     * */
    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        if (!Objects.nonNull(user.getUsername()) || !Objects.nonNull(user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.updateUser(details.getUsername(), user.getUsername(), user.getPassword());
        return ResponseEntity.ok().build();
    }

    /*
     * Delete user
     * */
    @DeleteMapping
    public ResponseEntity<Object> deleteUser() {
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.getUserByUsername(details.getUsername());

        for (String id: user.getDocuments()) {
            docService.deleteUser(id, user.getId());
        }

        userService.deleteUser(user.getId());
        return ResponseEntity.noContent().build();
    }
}
