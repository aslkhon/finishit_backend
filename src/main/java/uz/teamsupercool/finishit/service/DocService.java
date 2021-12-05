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

package uz.teamsupercool.finishit.service;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import uz.teamsupercool.finishit.model.*;
import uz.teamsupercool.finishit.repository.DocRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
* Doc service is used to handle operations over Document and its content as Tasks and Comments
*
* Methods are self-descriptive/
* */

@Service
public record DocService(DocRepository repository) {

    public Doc addDoc(Doc doc, String userId) {
        final ArrayList<String> users = new ArrayList<>();
        users.add(userId);
        return repository.insert(new Doc(null, doc.getTitle(), doc.getDescription(), LocalDateTime.now(), LocalDateTime.now(), users, new ArrayList<>()));
    }

    public Doc getDocById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Cannot Find Doc by ID %s", id)));
    }

    public void updateDoc(Doc doc) {
        final Doc savedDoc = getDocById(doc.getId());
        savedDoc.setTitle(doc.getTitle());
        savedDoc.setDescription(doc.getDescription());

        repository.save(savedDoc);
    }

    public boolean deleteDoc(String id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void addUser(String docId, String userId) {
        final Doc savedDoc = getDocById(docId);

        final List<String> users = savedDoc.getUsers();

        users.add(userId);
        savedDoc.setUsers(users);

        repository.save(savedDoc);
    }

    public boolean deleteUser(String docId, String userId) {
        final Doc savedDoc = getDocById(docId);

        final List<String> users = savedDoc.getUsers();

        try {
            users.remove(userId);

            savedDoc.setUsers(users);

            repository.save(savedDoc);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String addTask(String docId, Task task) {
        final Doc savedDoc = getDocById(docId);

        final List<DocContent> contents = savedDoc.getContents();

        final ObjectId id = new ObjectId();

        contents.add(new Task(
                id.toString(),
                task.getContent(),
                task.getDeadline(),
                false,
                DocContentType.TASK
        ));

        savedDoc.setContents(contents);

        repository.save(savedDoc);

        return id.toString();
    }

    public Task getTaskById(Doc doc, String taskId) {
        final List<DocContent> contents = doc.getContents();

        for (DocContent content: contents) {
            if (content.getId().equals(taskId) && content.getType() == DocContentType.TASK) {
                return (Task) content;
            }
        }

        throw new RuntimeException("Task not found");
    }

    public Comment getCommentById(Doc doc, String commentId) {
        final List<DocContent> contents = doc.getContents();

        for (DocContent content: contents) {
            if (content.getId().equals(commentId) && content.getType() == DocContentType.COMMENT) {
                return (Comment) content;
            }
        }

        throw new RuntimeException("Task not found");
    }

    public int getIndexInList(List<DocContent> contents, String id) {
        int i = 0;
        for (DocContent content: contents) {
            if (content.getId().equals(id)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public void updateTask(String docId, String taskId, Task task) throws RuntimeException {
        Doc doc = getDocById(docId);
        Task savedTask = getTaskById(doc, taskId);

        savedTask.setContent(task.getContent());
        savedTask.setDeadline(task.getDeadline());

        int index = getIndexInList(doc.getContents(), taskId);

        doc.getContents().set(index, savedTask);

        repository.save(doc);
    }

    public String addComment(String docId, Comment comment) {
        final Doc savedDoc = getDocById(docId);

        final List<DocContent> contents =  savedDoc.getContents();

        final ObjectId id = new ObjectId();

        contents.add(new Comment(
                id.toString(),
                comment.getContent(),
                DocContentType.COMMENT
        ));

        savedDoc.setContents(contents);

        repository.save(savedDoc);

        return id.toString();
    }

    public void updateComment(String docId, String commentId, Comment comment) throws RuntimeException {
        Doc doc = getDocById(docId);
        Comment savedComment = getCommentById(doc, commentId);

        savedComment.setContent(comment.getContent());

        int index = getIndexInList(doc.getContents(), commentId);

        doc.getContents().set(index, savedComment);

        repository.save(doc);
    }

    public void deleteDocContent(String docId, String contentId) {
        Doc doc = getDocById(docId);

        int index = getIndexInList(doc.getContents(), contentId);

        if (index == -1) {
            throw new RuntimeException("Content not found");
        }

        doc.getContents().remove(index);

        repository.save(doc);
    }

    public void setStatus(String docId, String taskId, boolean isDone) throws RuntimeException {
        Doc doc = getDocById(docId);

        Task task = getTaskById(doc, taskId);

        task.setDone(isDone);

        repository.save(doc);
    }
}
