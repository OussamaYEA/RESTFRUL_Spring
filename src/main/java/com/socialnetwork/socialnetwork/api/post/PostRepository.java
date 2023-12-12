package com.socialnetwork.socialnetwork.api.post;

import com.socialnetwork.socialnetwork.api.tag.Tag;
import com.socialnetwork.socialnetwork.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByOwner(User user);


}
