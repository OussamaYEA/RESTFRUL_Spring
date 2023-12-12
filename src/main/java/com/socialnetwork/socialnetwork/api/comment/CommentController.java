package com.socialnetwork.socialnetwork.api.comment;

import com.socialnetwork.socialnetwork.api.post.Post;
import com.socialnetwork.socialnetwork.api.post.PostService;
import com.socialnetwork.socialnetwork.api.user.User;
import com.socialnetwork.socialnetwork.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;



    @PostMapping("/comment/{userId}/{postId}")
    public ResponseEntity<Object> newComment(@RequestBody Comment comment, @PathVariable int userId, @PathVariable int postId){
        Optional<Post> post = postService.getPostById(postId);
        Optional<User> user = userService.getUserById(userId);
        if (post.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        comment.setOwner(user.get());
        comment.setPost(post.get());

        commentService.newComment(comment);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable int commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(commentId, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<Object> getComments(@RequestParam(defaultValue = "0") Integer pageNo,
                                             @RequestParam(defaultValue = "0") Integer pageSize){
        List <Comment> comments = commentService.getComments(pageNo, pageSize);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/post/{postId}")
    public ResponseEntity<Object> getCommentsByPost(@PathVariable int postId, @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "0") Integer pageSize){
        Optional<Post> post = postService.getPostById(postId);
        if (post.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List <Comment> comments = commentService.getCommentsByPost(post.get(), pageNo, pageSize);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<Object> getCommentsByUser(@PathVariable int userId, @RequestParam(defaultValue = "0") Integer pageNo,
                                                    @RequestParam(defaultValue = "1") Integer pageSize){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List <Comment> comments = commentService.getCommentsByUser(user.get(), pageNo, pageSize);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
