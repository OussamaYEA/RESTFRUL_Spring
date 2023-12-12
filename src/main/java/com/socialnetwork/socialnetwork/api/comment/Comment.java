package com.socialnetwork.socialnetwork.api.comment;

import com.socialnetwork.socialnetwork.api.post.Post;
import com.socialnetwork.socialnetwork.api.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Integer id ;
    @Column(nullable = false)
    private  String message;
    @ManyToOne(optional = false)
    private User owner;

    @ManyToOne(optional = false)
    private Post post;

    private Timestamp publishDate = Timestamp.from(Instant.now());

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
