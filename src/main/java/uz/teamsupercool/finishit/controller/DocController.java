package uz.teamsupercool.finishit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.teamsupercool.finishit.model.AddUserRequest;
import uz.teamsupercool.finishit.model.Doc;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("api/doc")
public class DocController {
    final DocService docService;
    final UserService userService;

    public DocController(DocService docService, UserService userService) {
        this.docService = docService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> addDoc(@RequestBody Doc doc) {
        if (!Objects.nonNull(doc.getTitle()) || !Objects.nonNull(doc.getDescription())) {
            return ResponseEntity.badRequest().build();
        }

        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.getUserByUsername(details.getUsername());

        final Doc newDoc = docService.addDoc(doc, user.getId());
        userService.addDoc(user.getId(), newDoc.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Object> getDocs() {
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.getUserByUsername(details.getUsername());

        final ArrayList<Doc> docs = new ArrayList<>();

        for (String id: user.getDocuments()) {
            docs.add(docService.getDocById(id));
        }

        return ResponseEntity.ok(docs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDoc(@PathVariable String id) {
        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            return ResponseEntity.ok(docService.getDocById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDoc(@PathVariable String id, @RequestBody Doc doc) {
        if (!Objects.nonNull(doc.getTitle()) || !Objects.nonNull(doc.getDescription())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            doc.setId(id);
            docService.updateDoc(doc);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDoc(@PathVariable String id) {
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        final User user = userService.getUserByUsername(details.getUsername());

        boolean userHasDoc = false;

        for (String docId: user.getDocuments()) {
            if (docId.equals(id)) {
                userHasDoc = true;
                break;
            }
        }

        if (userHasDoc) {
            if (userService.deleteUsersDoc(user.getId(), id) && docService.deleteDoc(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/users")
    public ResponseEntity<Object> addUser(@PathVariable String id, @RequestBody AddUserRequest request) {
        if (!Objects.nonNull(request.username())) {
            return ResponseEntity.badRequest().build();
        }

        User newUser;

        try {
            newUser = userService.getUserByUsername(request.username());
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }

        Doc doc = docService.getDocById(id);

        boolean docHasNewUser = false;
        for (String userId: doc.getUsers()) {
            if (userId.equals(newUser.getId())) {
                docHasNewUser = true;
                break;
            }
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id) && !docHasNewUser) {
            userService.addDoc(newUser.getId(), doc.getId());
            docService.addUser(doc.getId(), newUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id, @PathVariable String userId) {
        User newUser;

        try {
            newUser = userService.getUserByUsername(userId);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }

        Doc doc = docService.getDocById(id);

        boolean docHasNewUser = false;
        for (String newUserId: doc.getUsers()) {
            if (newUserId.equals(newUser.getId())) {
                docHasNewUser = true;
                break;
            }
        }

        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id) && docHasNewUser) {
            if (userService.deleteUsersDoc(newUser.getId(), doc.getId()) || docService.deleteUser(doc.getId(), newUser.getId())) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
