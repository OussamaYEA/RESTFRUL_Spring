package com.socialnetwork.socialnetwork.api.comment;

import com.socialnetwork.socialnetwork.api.post.Post;
import com.socialnetwork.socialnetwork.api.user.User;

import java.util.List;

public interface CommentService {
    void newComment(Comment comment);
    void deleteComment(Integer id);

    List<Comment> getComments(int page, int size);
    List<Comment> getCommentsByPost(Post post, int page, int size);

    List<Comment> getCommentsByUser(User user, int page, int size);


}
