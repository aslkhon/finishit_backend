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

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/*
* An abstract class that contains common field of Comment and Task inside a Document
*
* Used for polymorphism in storing different class objects in one document
* */

public abstract class DocContent {
    @Id
    String id;

    @Field(name = "type")
    DocContentType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DocContentType getType() {
        return type;
    }

    public void setType(DocContentType type) {
        this.type = type;
    }
}