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

import java.util.Objects;

/*
 * Extends DocContent
 *
 * Consists of Content.
 * All methods in class are simple accessors and mutators
 * */

public class Comment extends DocContent {
    @Field(name = "content")
    private String content;

    public Comment() {
    }

    public Comment(String id, String content, DocContentType type) {
        this.id = id;
        this.content = content;
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
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && type == comment.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, type);
    }
}
