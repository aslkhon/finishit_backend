package uz.teamsupercool.finishit.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document("docs")
public class Doc {
        @Id
        String id;

        @Field(name = "title")
        String title;

        @Field(name = "description")
        String description;

        @Field(name = "createdAt")
        LocalDateTime createdAt;

        @Field(name = "updatedAt")
        LocalDateTime updatedAt;

        @Field(name = "users")
        List<String> users;

        @Field(name = "contents")
        List<DocContent> contents;

        public Doc(String id, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, List<String> users, List<DocContent> contents) {
                this.id = id;
                this.title = title;
                this.description = description;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
                this.users = users;
                this.contents = contents;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public List<String> getUsers() {
                return users;
        }

        public void setUsers(List<String> users) {
                this.users = users;
        }

        public List<DocContent> getContents() {
                return contents;
        }

        public void setContents(List<DocContent> contents) {
                this.contents = contents;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Doc doc = (Doc) o;
                return Objects.equals(id, doc.id) && Objects.equals(title, doc.title) && Objects.equals(description, doc.description) && Objects.equals(createdAt, doc.createdAt) && Objects.equals(updatedAt, doc.updatedAt) && Objects.equals(users, doc.users) && Objects.equals(contents, doc.contents);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, title, description, createdAt, updatedAt, users, contents);
        }

        @Override
        public String toString() {
                return "Doc{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", description='" + description + '\'' +
                        ", createdAt=" + createdAt +
                        ", updatedAt=" + updatedAt +
                        ", users=" + users +
                        ", contents=" + contents +
                        '}';
        }
}