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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.teamsupercool.finishit.model.User;
import uz.teamsupercool.finishit.service.DocService;
import uz.teamsupercool.finishit.service.UserService;

/*
* Abstract class with util methods.
* */
abstract public class UserDocUtils {

    /*
    * Method to check if document belongs to user.
    *
    * Gets user by from token and then checks all his documents whether user has a doc.
    * */
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

    /*
    * Method to delete Task or Comment from the Document
    * */
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
