package uz.teamsupercool.finishit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

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