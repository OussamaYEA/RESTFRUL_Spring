package com.socialnetwork.socialnetwork.api.comment;


import com.socialnetwork.socialnetwork.api.post.Post;
import com.socialnetwork.socialnetwork.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPost(Post post);
    List<Comment> findByOwner(User owner);
}
