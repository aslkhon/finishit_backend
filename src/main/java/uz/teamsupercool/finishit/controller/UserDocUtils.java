package uz.teamsupercool.finishit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

abstract public class UserDocUtils {
    public static boolean doesUserHaveDoc(UserService service, SecurityContext context, String id) {
        final UserDetails details = (UserDetails) context.getAuthentication().getPrincipal();

        final User user = service.getUserByUsername(details.getUsername());

        boolean userHasDoc = false;

        for (String docId: user.getDocuments()) {
            if (docId.equals(id)) {
                userHasDoc = true;
                break;
            }
        }

        return userHasDoc;
    }

    public static ResponseEntity<Object> deleteDocContent(UserService userService, DocService docService, String id, String commentId) {
        if (UserDocUtils.doesUserHaveDoc(userService, SecurityContextHolder.getContext(), id)) {
            try {
                docService.deleteDocContent(id, commentId);
                return ResponseEntity.noContent().build();
            } catch (RuntimeException exception) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
