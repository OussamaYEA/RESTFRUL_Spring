package com.socialnetwork.socialnetwork.api.post;

import com.socialnetwork.socialnetwork.api.user.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void addPost(Post post);
    void updatePost(Post existPost, Post post);
    void deletePost(int id);
    Optional<Post> getPostById(int id);
    List<Post> getPostsByUser(User owner, int page, int size);
    List<Post> getPosts(String field, int page, int size);

}
