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

package uz.teamsupercool.finishit.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

/*
* Extends DocContent
*
* Consists of Content, Deadline, and isDone fields.
* All methods in class are simple accessors and mutators
* */

public class Task extends DocContent {
    @Field(name = "content")
    private String content;

    @Field(name = "deadline")
    private LocalDateTime deadline;

    @Field(name = "isDone")
    private boolean isDone;

    public Task() {
    }

    public Task(String id, String content, LocalDateTime deadline, boolean isDone, DocContentType type) {
        this.id = id;
        this.content = content;
        this.deadline = deadline;
        this.isDone = isDone;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public DocContentType getType() {
        return type;
    }

    public void setType(DocContentType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return isDone == task.isDone && Objects.equals(id, task.id) && Objects.equals(content, task.content) && Objects.equals(deadline, task.deadline) && type == task.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, deadline, isDone, type);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", deadline=" + deadline +
                ", isDone=" + isDone +
                ", type=" + type +
                '}';
    }
}
